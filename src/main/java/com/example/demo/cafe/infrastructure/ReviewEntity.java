package com.example.demo.cafe.infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.example.demo.auth.implement.BaseEntity;
import com.example.demo.cafe.domain.Cafe;
import com.example.demo.cafe.domain.CafeId;
import com.example.demo.cafe.domain.Review;
import com.example.demo.cafe.domain.ReviewId;
import com.example.demo.domain.DateAudit;
import com.example.demo.member.infrastructure.MemberEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Where(clause = "deleted_date IS NULL")
@Table(name = "review")
public class ReviewEntity extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "review_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cafe_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private CafeEntity cafe;

	//TODO 멤버 대신 StudyMember가 필요할 것 같다.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private MemberEntity member;

	@OneToMany(mappedBy = "review")
	private List<ReviewCafeTagEntity> reviewCafeTag = new ArrayList<>();

	public Review toReview() {
		return Review.builder()
			.id(new ReviewId(this.id))
			.tags(
				reviewCafeTag.stream()
					.map(tag -> tag.getCafeTag().getType())
					.collect(Collectors.toList())
			)
			.cafe(
				Cafe.builder()
					.id(new CafeId(cafe.getId()))
					.name(cafe.getName())
					.imgUrl(cafe.getMainImageUrl())
					.build()
			)
			.dateAudit(
				DateAudit.builder()
					.createdDate(this.getCreatedDate())
					.modifiedDate(this.getLastModifiedDate())
					.build()
			)
			.build();
	}
}
