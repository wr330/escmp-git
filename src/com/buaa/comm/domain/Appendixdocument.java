package com.buaa.comm.domain;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import com.buaa.comm.domain.Btreport;

@Entity
@Table(name="COMM_APPENDIXDOCUMENT")
public class  Appendixdocument implements Serializable {
	private static final long serialVersionUID = 1L;
    public Appendixdocument(){}
   public Appendixdocument(String oid) {
      this.oid=oid;
 	}	

	private String oid ;

	private String efile ;

	private Btreport btreport;
	
	@Id
		@Column(name = "OID_", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}

	@Column(name="EFILE_")
	public String getEfile() {
		return efile;
	}
	public void setEfile(String efile) {
		this.efile=efile;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "BTREPORT_")
    public Btreport getBtreport() {
		return btreport;
	}
	public void setBtreport(Btreport btreport) {
		this.btreport=btreport;
	}

}