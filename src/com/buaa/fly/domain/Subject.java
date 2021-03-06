package com.buaa.fly.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "FLY_SUBJECT")
public class Subject implements Serializable {
	private static final long serialVersionUID = 1L;

	public Subject() {
	}

	public Subject(String oid) {
		this.oid = oid;
	}

	private String oid;
	private Integer orderno;
	private String description;
	private String name;
	private String parentnode;
	private String ftype;
	private List<Subject> children;
	private List<Outlineexecution> outlineexecution;
	private String subjectno;

	@Id
	@Column(name = "OID_", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	@Column(name = "ORDERNO_")
	public Integer getOrderno() {
		return orderno;
	}

	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}

	@Column(name = "DESCRIPTION_")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "NAME_")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PARENTNODE_")
	public String getParentnode() {
		return parentnode;
	}

	public void setParentnode(String parentnode) {
		this.parentnode = parentnode;
	}

	@Column(name = "FTYPE_")
	public String getFtype() {
		return ftype;
	}

	public void setFtype(String ftype) {
		this.ftype = ftype;
	}

	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, mappedBy = "project")
	public List<Outlineexecution> getOutlineexecution() {
		return outlineexecution;
	}

	public void setOutlineexecution(List<Outlineexecution> outlineexecution) {
		this.outlineexecution = outlineexecution;
	}

	@Transient
	public List<Subject> getChildren() {
		return children;
	}

	public void setChildren(List<Subject> children) {
		this.children = children;
	}

	@Transient
	public String getSubjectno() {
		return subjectno;
	}

	public void setSubjectno(String subjectno) {
		this.subjectno = subjectno;
	}
}