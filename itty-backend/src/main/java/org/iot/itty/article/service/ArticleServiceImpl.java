package org.iot.itty.article.service;

import java.util.Date;
import java.util.List;

import org.iot.itty.article.aggregate.ArticleEntity;
import org.iot.itty.article.repository.ArticleRepository;
import org.iot.itty.dto.ArticleDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService{

	private final ArticleRepository articleRepository;
	private final ModelMapper mapper;

	@Autowired
	public ArticleServiceImpl(ArticleRepository articleRepository, ModelMapper mapper) {
		this.articleRepository = articleRepository;
		this.mapper = mapper;
	}

	@Override
	public List<ArticleDTO> selectAllArticleFromFreeBoard() {
		int articleCategory = 2;
		List<ArticleEntity> articleEntityList = articleRepository.findAllByArticleCategory(articleCategory);

		return articleEntityList
			.stream()
			.map(ArticleEntity -> mapper.map(ArticleEntity, ArticleDTO.class))
			.toList();
	}

	@Override
	public ArticleDTO registFreeBoardArticle(ArticleDTO requestArticleDTO) {
		ArticleEntity articleEntity = ArticleEntity.builder()
			.articleTitle(requestArticleDTO.getArticleTitle())
			.articleContent(requestArticleDTO.getArticleContent())
			.articleCreatedDate(new Date())
			.articleLastUpdatedDate(new Date())
			.articleCategory(2)		// 자유게시판 카테고리 번호: 2
			.articleViewCount(0)	// 게시글 등록 시 조회수는 0
			.userCodeFk(requestArticleDTO.getUserCodeFk())
			.build();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return mapper.map(articleRepository.save(articleEntity), ArticleDTO.class);
	}
}
