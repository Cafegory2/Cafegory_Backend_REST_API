//package com.example.demo.repository.review;
//
//import static org.assertj.core.api.Assertions.*;
//
//import java.util.List;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.implement.Page;
//
//import com.example.demo.config.JpaTest;
//import com.example.demo.implement.cafe.Cafe;
//import com.example.demo.implement.member.Member;
//import com.example.demo.implement.member.ThumbnailImage;
//import com.example.demo.implement.review.Review;
//import com.example.demo.helper.CafeSaveHelper;
//import com.example.demo.helper.MemberSaveHelper;
//import com.example.demo.helper.ReviewSaveHelper;
//import com.example.demo.helper.ThumbnailImageSaveHelper;
//import com.example.demo.util.PageRequestCustom;
//
//class ReviewRepositoryTest extends JpaTest {
//
//	@Autowired
//	private ReviewRepository reviewRepository;
//	@Autowired
//	private CafeSaveHelper cafePersistHelper;
//	@Autowired
//	private ThumbnailImageSaveHelper thumbnailImagePersistHelper;
//	@Autowired
//	private MemberSaveHelper memberPersistHelper;
//	@Autowired
//	private ReviewSaveHelper reviewPersistHelper;
//
//	@Test
//	void findAllByCafeId() {
//		//given
//		Cafe cafe = cafePersistHelper.saveCafe();
//		ThumbnailImage thumb = thumbnailImagePersistHelper.saveThumbnailImage();
//		Member member = memberPersistHelper.saveMember(thumb);
//		reviewPersistHelper.saveReview(cafe, member);
//		reviewPersistHelper.saveReview(cafe, member);
//		//when
//		List<Review> reviews = reviewRepository.findAllByCafeId(cafe.getId());
//		//then
//		assertThat(reviews.size()).isEqualTo(2);
//	}
//
//	@Test
//	@DisplayName("페이징 기본값")
//	void findAllWithPagingByCafeId() {
//		//given
//		Cafe cafe = cafePersistHelper.saveCafe();
//		ThumbnailImage thumb = thumbnailImagePersistHelper.saveThumbnailImage();
//		Member member = memberPersistHelper.saveMember(thumb);
//
//		for (int i = 0; i < 20; i++) {
//			reviewPersistHelper.saveReview(cafe, member);
//			reviewPersistHelper.saveReview(cafe, member);
//		}
//		//when
//		Page<Review> pagedReviews = reviewRepository.findAllWithPagingByCafeId(cafe.getId(),
//			PageRequestCustom.createByDefault());
//		//then
//		assertThat(pagedReviews.getContent().size()).isEqualTo(10);
//		assertThat(pagedReviews.getTotalPages()).isEqualTo(4);
//		assertThat(pagedReviews.getSize()).isEqualTo(10);
//		assertThat(pagedReviews.getTotalElements()).isEqualTo(40);
//	}
//
//}
