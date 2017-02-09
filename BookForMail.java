package com.fencer.su;



 public class BookForMail{
   private JFrame jfr;
   private JButton jbuttonContact;
   private JButton jbuttonAdd;
   private JButton jbuttonDelete;
   private JButton jbuttonSend;
   private JButton jbuttonExit;
   private JLabel allContact;
   
   public BookForMail(){
          DataBase.createTable();
          DataBase.selectFromTable();
	  jfr = new JFrame("My contacts");
          jfr.setSize(250, 350);
          jfr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   JPanel pan = new JPanel();
	   pan.setSize(200, 300);
	   pan.setLayout(new GridLayout(6, 1));
	   jbuttonContact = new JButton("show all Book");
	   jbuttonAdd = new JButton("add");
	   jbuttonDelete = new JButton("delete");
	   jbuttonSend = new JButton("send");
	   jbuttonExit = new JButton("exit");
	   allContact = new JLabel("", SwingConstants.CENTER);
	   JScrollPane scroll = new JScrollPane(allContact);
           scroll.setViewportBorder(BorderFactory.createLineBorder(Color.GREEN, 7));
	   
	   pan.add(jbuttonContact);
	   pan.add(jbuttonAdd);
	   pan.add(jbuttonDelete);
	   pan.add(jbuttonSend);
	   pan.add(scroll);
	   pan.add(jbuttonExit);
           
   jbuttonContact.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent ae){
                if (ae.getActionCommand().equals("show all Book")){
                    String s, g = "";
                    int lst = DataBase.getList().size();
                    for (int i = 0; i < lst; i++){
                     s = "" + (i + 1) + "<br>mail:    " + DataBase.getList().get(i).getMailSql()
		         + "<br>name:    " + DataBase.getList().get(i).getNameSql() + "<br>"; 
                     g = g + s;
                    }
                    allContact.setText("<html>" + g); 
                 }
                
            }
        });
		
		
   jbuttonAdd.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent ae){
              if (ae.getActionCommand().equals("add")){
                String mail = JOptionPane.showInputDialog("Enter mail");
                String name = JOptionPane.showInputDialog("Enter name");
               
                ContactSql javaContact = new ContactSql(mail, name);
                String pol = "insert into namemail values ('" + javaContact.getMailSql() + "', "
				+ "'" + javaContact.getNameSql() + "');";
               
                DataBase.insertToTable(pol);
                DataBase.getList().add(javaContact);
                jbuttonContact.doClick(); 
              }
          }
      });
	  
	  
   jbuttonDelete.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent ae){
           if (ae.getActionCommand().equals("delete")){
                String number;
                int numb;
                number = JOptionPane.showInputDialog("<html>Enter number.<br>"
                 + "MIN >= 1"      + " MAX <= " + DataBase.getList().size());
                numb = Integer.parseInt(number); 
               
                String pol = "delete from namemail where mail='" + DataBase.getList().get(numb - 1).getMailSql() + "';";
                DataBase.deleteFromTable(pol);
                DataBase.getList().remove(numb - 1);
                jbuttonContact.doClick();
               }            
           }            
      });

   jbuttonSend.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent ae){
              if (ae.getActionCommand().equals("send")){
                String mail = JOptionPane.showInputDialog("Enter number bookForMail");
				int intMail = Integer.parseInt(mail);
                MySendMail fileSend = new MySendMail();
				fileSend.sendMailFile(DataBase.getList().get(intMail - 1).getMailSql()); 
              }
          }
      });	  
   
  jbuttonExit. addActionListener(new ActionListener(){
     public void actionPerformed(ActionEvent ae){
        System.exit(0);
           }  
        });    
   
        jfr.add(pan);   
        jfr.setVisible(true); 
   }
   
}

