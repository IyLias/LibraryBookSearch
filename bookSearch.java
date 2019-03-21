import java.net.URL;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class BookSearch {
	
	static final String seoul_lib[]={"����ñ���û ����������", "����ñ���û ����������" ,"����ñ���û ����������","����ñ���û ����������", "����ñ���û �������н���", "����ñ���û ��ô������", "����ñ���û ���ε�����", "����ñ���û ���굵����", "����ñ���û �������н���", "����ñ���û ����������", "����ñ���û ���빮������", "����ñ���û ���۵�����", "����ñ���û ������������а�", "����ñ���û ��������н���", "����ñ���û ���빮������", "����ñ���û ���ĵ�����", "����ñ���û ��õ������", "����ñ���û ��̵�����", "����ñ���û ����������н���", "����ñ���û ��굵����", "����ñ���û ����������", "����ñ���û ���ε�����"};
	final String lib_id[]={"111003","111004","111005","111006","111007","111008","111009","111010","111022","111011","111012","111013","111031","111014","111016","111030","111015","111017","111018","111019","111020","111021"};
	final String lib_initial[]={"gnlib","gdlib","gslib","gplib","gdllc","gclib","grlib","nslib","nwllc","dblib","ddmlib","djlib","ahyon","sdmlib","splib","yclib","childlib","ydpllc","yslib","jdlib","jnlib"};
		
	String srch_sce;
	Book books[];
	BookInfoParser bookParser;
	
	public BookSearch(){
		
			books = new Book[10];
			bookParser = new BookInfoParser();
			
			searchButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					
					try{
						
						if(e.getSource() == searchButton ){
					    	
							int listIdx=List.getSelectedIndex();
							String url = "http://"+ lib_initial[listIdx] +".sen.go.kr/" + lib_initial[listIdx] + "/intro/search/index.do?menu_idx=4&locExquery=" + lib_id[listIdx] + "&editMode=normal&officeNm=" + seoul_lib[listIdx] + "&lectureNm=&mainSearchType=on&search_text=";
						
							String query = textFieldForSearch.getText();
							Document qry_doc = Jsoup.connect(url+query).get();
							srch_sce = qry_doc.text().toString();
					    
							if(srch_sce.contains("ã���ô� �ڷᰡ �����ϴ�") == false){
					    	
								Elements contents1 = qry_doc.select(".author");
								Elements contents2 = qry_doc.select(".data");
								Elements contents3 = qry_doc.select(".site");
								Elements contents4 = qry_doc.select(".txt");
								Elements contents5 = qry_doc.select(".tit2");
								Elements contents6 = qry_doc.select(".thumb");
					    
								String pageSource1 = contents1.toString(); //���� ����ó ���࿬�� ���� �Ľ̼ҽ�
								String pageSource2 = contents2.toString(); // ISBN �ڷ����� û����ȣ ���� �Ľ̼ҽ�
								String pageSource3 = contents3.toString(); // ��������ġ �ڷ����ġ ���� �Ľ̼ҽ�
								String pageSource4 = contents4.toString(); // �ڷ���� ���� �Ľ̼ҽ�
								String pageSource5 = contents5.toString(); // å �̸� ���� �Ľ̼ҽ�
								String pageSource6 = contents6.toString(); // å ���� �̹��� ��� ���� �Ľ̼ҽ�
								
								for(int i=0;i<10;i++){
									books[i] = new Book(i+1);
								}
					    
								for(int i=0;i<10;i++){
									books[i].setBookAuthor(bookParser.parseBookAuthor(pageSource1, i));
									books[i].setBookPublisher(bookParser.parseBookPublisher(pageSource1, i));
									books[i].setBookIssueYear(bookParser.parseBookIssueYear(pageSource1, i));
									books[i].setBookISBN(bookParser.parseBookISBN(pageSource2,i));
									books[i].setBookDataType(bookParser.parseBookDataType(pageSource2,i));
									books[i].setBookCallNumber(bookParser.parseBookCallNumber(pageSource2,i));
									books[i].setBookWhichLibrary(bookParser.parseBookWhichLibrary(pageSource3,i));
									books[i].setBookWhereIs(bookParser.parseBookWhereIs(pageSource3,i));
									books[i].setBookCurrentCondition(bookParser.parseBookCurrentCondition(pageSource4,i));
									books[i].setBookName(bookParser.parseBookName(pageSource5,i));
									books[i].setBookImagePath(bookParser.parseBookImagePath(pageSource6,i));
									
									books[i].setLabelTexts();
									addBookDatas(books[i]);
									
								}	
					   
								for(int i=0;i<10;i++){
									System.out.println(books[i].getBookAuthor());
									System.out.println(books[i].getBookPublisher());
									System.out.println(books[i].getBookIssueYear());
									System.out.println(books[i].getBookISBN());
									System.out.println(books[i].getBookDataType());
									System.out.println(books[i].getBookCallNumber());
									System.out.println(books[i].getBookWhichLibrary());
									System.out.println(books[i].getBookWhereIs());
									System.out.println(books[i].getBookCurrentCondition());
									System.out.println(books[i].getBookName());
									System.out.println(books[i].getBookImagePath());
									
									System.out.println("\n");
								}
					   
								searchResultFrame.setSize(800,1000);
								searchResultFrame.setLayout(null);
								searchResultFrame.setVisible(true);
								
							} // if contains statements
							else{
								
								// ã�� �ڷᰡ ���� �� ȭ�鿡 ã�� �ڷᰡ ���ٰ� ���
								System.out.println("ã�� �ڷᰡ ���� �ٽ� �˻��غ�");
							
							}
							 
					    	
						} // if searchButton statements
						
					}catch(Exception ex){
						ex.getStackTrace();
						System.out.println("Error while you search");
					}
				
				} //actionPerformed
			});
		
	}
		
	public static void main(String[] args) {
		
		new BookSearch();
		
	}

}