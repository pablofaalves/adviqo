package com.adviqo.searchservice.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.adviqo.searchservice.enums.AvailabilityEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Expert entity class
 * 
 * @author Pablo Alves
 * @since 12/11/2019
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Expert {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Basic(optional = false)
	@Column(length = 70, nullable = false)
	private String name;
	
	@Lob
	@Basic(optional = false)
	@Column(nullable = false)
	private String description;
	
	@Basic(optional = false)
	@Column(scale = 2, precision = 5, nullable = false)
	private double pricePerMinute;
	
	@Basic(optional = false)
	@Column(length = 20, nullable = false)
	private String language;
	
	@Enumerated(EnumType.STRING)
	@Basic(optional = false)
	@Column(nullable = false)
	private AvailabilityEnum availability;
}
