package com.cxytiandi.test;

import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import com.cxytiandi.mongo.document.Article;
import com.cxytiandi.mongo.repository.ArticleRepositor;

/**
 * 使用Repositor操作数据
 * @author yinjihuan
 *
 */
public class ArticleRepositorTest {
	private static ArticleRepositor articleRepositor;
	static {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		articleRepositor = context.getBean(ArticleRepositor.class);
	}
	
	public static void main(String[] args) {
		//findAll();
		//findByAuthor();
		//findByAuthorAndTitle();
		//findByAuthorIgnoreCase();
		//findByAuthorOrderByVisitCountDesc();
		//findByAuthorOrderByVisitCountAsc();
		//findByAuthorBySort();
		findByPage();
	}
	
	/**
	 * 查询所有
	 * @author yinjihuan
	 */
	private static void findAll() {
		Iterable<Article> articles = articleRepositor.findAll();
		articles.forEach(article ->{
			System.out.println(article.getId());
		});
	}
	
	/**
	 * 根据作者查询
	 * @author yinjihuan
	 */
	private static void findByAuthor() {
		List<Article> articles = articleRepositor.findByAuthor("jason");
		articles.forEach(article ->{
			System.out.println(article.getId());
		});
	}
	
	/**
	 * 分解作者和标题查询
	 * @author yinjihuan
	 */
	private static void findByAuthorAndTitle() {
		List<Article> articles = articleRepositor.findByAuthorAndTitle("yinjihuan", "MongoTemplate的基本使用");
		articles.forEach(article ->{
			System.out.println(article.getId());
		});
	}
	
	/**
	 * 根据作者查询，忽略大小写
	 * @author yinjihuan
	 */
	private static void findByAuthorIgnoreCase() {
		List<Article> articles = articleRepositor.findByAuthorIgnoreCase("JASON");
		articles.forEach(article ->{
			System.out.println(article.getId());
		});
	}
	
	/**
	 * 忽略所有参数的大小写
	 * @author yinjihuan
	 */
	private static void findByAuthorAndTitleAllIgnoreCase() {
		List<Article> articles = articleRepositor.findByAuthorAndTitleAllIgnoreCase("JASON", "MONGOTEMPLATE的基本使用");
		articles.forEach(article ->{
			System.out.println(article.getId());
		});
	}
	
	/**
	 * 根据作者查询，并且以访问次数降序排序显示
	 * @author yinjihuan
	 */
	private static void findByAuthorOrderByVisitCountDesc() {
		List<Article> articles = articleRepositor.findByAuthorOrderByVisitCountDesc("yinjihuan");
		articles.forEach(article ->{
			System.out.println(article.getAuthor());
		});
	}
	

	/**
	 * 根据作者查询，并且以访问次数升序排序显示
	 * @author yinjihuan
	 */
	private static void findByAuthorOrderByVisitCountAsc() {
		List<Article> articles = articleRepositor.findByAuthorOrderByVisitCountAsc("yinjihuan");
		articles.forEach(article ->{
			System.out.println(article.getAuthor());
		});
	}
	
	/**
	 * 自带排序条件
	 * @author yinjihuan
	 */
	private static void findByAuthorBySort() {
		List<Article> articles = articleRepositor.findByAuthor("yinjihuan", new Sort(Direction.ASC, "VisitCount"));
		articles.forEach(article ->{
			System.out.println(article.getAuthor());
		});
	}
	
	/**
	 * 分页查询所有，并且排序
	 */
	private static void findByPage() {
		int page = 1;
		int size = 2;
		Pageable pageable = new PageRequest(page, size,new Sort(Direction.ASC, "VisitCount"));
		Page<Article> pageInfo = articleRepositor.findAll(pageable);
		//总数量
		System.out.println(pageInfo.getTotalElements());
		//总页数
		System.out.println(pageInfo.getTotalPages());
		for (Article article : pageInfo.getContent()) {
			System.out.println(article.getAuthor());
		}
	}
}
