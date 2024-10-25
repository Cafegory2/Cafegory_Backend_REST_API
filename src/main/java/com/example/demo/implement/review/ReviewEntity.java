package com.example.demo.implement.review;

import javax.persistence.*;

import org.hibernate.annotations.Where;

import com.example.demo.implement.cafe.CafeEntity;
import com.example.demo.implement.member.MemberEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Where(clause = "deleted_date IS NULL")
@Table(name = "review")
public class ReviewEntity {

	@Id
	@GeneratedValue
	@Column(name = "review_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cafe_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private CafeEntity cafe;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private MemberEntity member;

	@OneToMany(mappedBy = "review")
	private List<ReviewCafeTagEntity> reviewCafeTag = new ArrayList<>();

	@Builder
	private ReviewEntity(CafeEntity cafe, MemberEntity member) {
		this.cafe = cafe;
		this.member = member;
	}
}
