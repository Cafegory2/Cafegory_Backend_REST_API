package com.example.demo.implement.cafe;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.demo.implement.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Where(clause = "deleted_date IS NULL")
@Table(name = "business_hour")
public class BusinessHourEntity extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "business_hour_id")
	private Long id;

	@Column(name = "day_of_week")
	private DayOfWeek dayOfWeek;

	/// columnDefinition 옵션은 나중에 DB에 직접 적용하고 삭제할 것
	@Column(columnDefinition = "TIME(6)")
	private LocalTime openingTime;
	@Column(columnDefinition = "TIME(6)")
	private LocalTime closingTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cafe_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private CafeEntity cafeEntity;

	@Builder
	private BusinessHourEntity(DayOfWeek dayOfWeek, LocalTime openingTime, LocalTime closingTime, CafeEntity cafeEntity) {
		this.dayOfWeek = dayOfWeek;
		this.openingTime = openingTime;
		this.closingTime = closingTime;
		this.cafeEntity = cafeEntity;
	}

		public boolean existsMatchingDayOfWeek(LocalDateTime now) {
			try {
				return now.getDayOfWeek().equals(this.dayOfWeek);
			} catch (IllegalArgumentException e) {
				return false;
			}
		}

		public boolean matchesDayOfWeek(DayOfWeek dayOfWeek) {
			return this.dayOfWeek.equals(dayOfWeek);
		}
}
