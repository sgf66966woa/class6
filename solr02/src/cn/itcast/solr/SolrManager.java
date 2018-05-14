package cn.itcast.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrManager {

	@Test
	public void testAdd() throws Exception {
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/collection1");
		for (int i = 1; i < 6; i++) {
			SolrInputDocument doc = new SolrInputDocument();
			doc.addField("id", i+"");
			doc.addField("name", "solr创建"+i);
			doc.addField("content", "solr创建"+i);
			solrServer.add(doc);
			
		}
		
		solrServer.commit();
		
	}
	
	@Test
	public void testUpdate() throws Exception{
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/collection1");
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", "1");
		doc.addField("name", "solr创建---");
		doc.addField("content", "solr创建---");
		solrServer.add(doc);
		solrServer.commit();
	}
	
	@Test
	public void testDelete() throws Exception {
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/collection1");
//		solrServer.deleteById("2");
		solrServer.deleteByQuery("name:添加");
		solrServer.commit();
	}
	
	@Test
	public void testQuery() throws Exception {
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/collection1");
		SolrQuery solrQuery = new SolrQuery();
		//solrQuery是查询索引库的类，它的add方法的参数是查询语法；它的set方法的参数有两个，第一个是查询语法（solrcore中的q标签，见网页）
		//第二个是要查询的属性名称，虽然没有name，content等字段，但是solr中有默认查询df（可以给查询条件默认加上name）
//		solrQuery.add("name:solr");
		solrQuery.set("q", "solr");
		//QueryResponse是solrquery查询的结果对象，通过solrServer获取
		QueryResponse queryResponse = solrServer.query(solrQuery);
		//SolrDocumentList是封装查询结果的一个list集合，它是arraylist的一个子类
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		System.out.println("总条数："+solrDocumentList.getNumFound());
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("name"));
			System.out.println(solrDocument.get("content"));
		}
		
	}
}
