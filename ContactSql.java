package com.fencer.su;




public class ContactSql{
  private String mailSql = null;
  private String nameSql = null;
  
  ContactSql(){}
  
  ContactSql(String mailSql, String nameSql){
     this.mailSql = mailSql;
     this.nameSql = nameSql;
  }
  
  public String getMailSql(){
    return mailSql;
  }
 
  public void  setMailSql(String mailSql){
     this.mailSql = mailSql;  
  }
  
  public String getNameSql(){
    return nameSql;
  }
 
  public void  setNameSql(String nameSql){
     this.nameSql = nameSql;  
  }
}

    