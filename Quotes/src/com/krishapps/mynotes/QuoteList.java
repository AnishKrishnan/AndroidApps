package com.krishapps.mynotes;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import android.content.Context;

public class QuoteList {

	
	private ArrayList<Quote> quotesList;
	FileOutputStream outputStream;
	ObjectOutputStream objectOutputStream;
	
	FileInputStream fileInputStream;
	ObjectInputStream objectInputStream;
	Context currentContext;
	
	private final static String LIST_FILE_NAME = "quoteData.txt";
	private final static String LINE_TERMINATOR = "\n";
	private final static String QUOTE_TERMINATOR = "~~";
	
	public QuoteList(Context context, int listSize){
		quotesList = new ArrayList<Quote>();
		currentContext = context;
		//generateRandomList(listSize);
		readQuoteList();
	}
	
	public ArrayList<Quote> getQuotesList(){
		return quotesList;
	}
	
	private void generateRandomList(int listSize){
		
		for(int i = 0; i < listSize; i++){
			Quote temp = new Quote("askdlfslklajflksajf;lksadjflkasdjflk;adsjf lsajfsadkljfwdakljfsk;ljfadslfj sadfj sadklfjadsklfjsadkl fjadsklfjadslkfja;skjf;adslffjsd","Anish", 2012);
			quotesList.add(temp);
		}
	}
	
	/*private void readQuoteList(){
		try{
			fileInputStream = new FileInputStream(LIST_FILE_NAME);
			objectInputStream = new ObjectInputStream(fileInputStream);
			
			quotesList = (ArrayList<Quote>)objectInputStream.readObject();
			objectInputStream.close();
			
			
		}catch(FileNotFoundException e){
			e.printStackTrace();
			
		}catch(IOException e2){
			e2.printStackTrace();
			
		}catch(ClassNotFoundException e3){
			e3.printStackTrace();
		}
		
	}*/
	
	private void readQuoteList(){
		
		try{

			//fileInputStream = currentContext.openFileInput(LIST_FILE_NAME);
		//	File file = new File(currentContext.getFilesDir(), LIST_FILE_NAME);
			InputStream inputStream = currentContext.openFileInput(LIST_FILE_NAME);
			
			if(inputStream != null){
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				Scanner scan = new Scanner(inputStreamReader);
				if(!scan.hasNext()){
					//generateRandomList(10);
					quotesList.add(new Quote(currentContext.getFilesDir().toString(),fileInputStream.toString(),1995));
					return;
				}
				
				String temp= scan.nextLine();
				
				int size = Integer.parseInt(temp.replaceAll("(\\r|\\n)",""));
				String author;
				String quoteText = "";
				int year = 0;
				
				for(int i = 0; i < size; i++){
					
					author = scan.nextLine();
					temp= scan.nextLine();
					year = Integer.parseInt(temp.replaceAll("(\\r|\\n)",""));;
					
					String line = scan.nextLine();
					while(!line.equals(QUOTE_TERMINATOR)){
						quoteText += line;
						line = scan.nextLine();
					}
					
					Quote q = new Quote(quoteText, author, year);
					quotesList.add(q);
					author = "";
					year = 0;
					quoteText = "";
				}
				
				scan.close();
			}
	
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
/*	private void writeQuoteList(){
		
		try{
			outputStream = new FileOutputStream(LIST_FILE_NAME);
			
			objectOutputStream = new ObjectOutputStream(outputStream);
			objectOutputStream.writeObject(quotesList);
			objectOutputStream.flush();
			objectOutputStream.close();
			
		}catch(FileNotFoundException e){
			e.printStackTrace();
			
		}catch(IOException e2){
			e2.printStackTrace();
			
		}
		
	}*/
	
	private void writeQuoteList(){
		
		try{
			//File file = new File(currentContext.getFilesDir(),LIST_FILE_NAME);
			outputStream = currentContext.openFileOutput(LIST_FILE_NAME, Context.MODE_PRIVATE);
		//	PrintWriter writer = new PrintWriter(outputStream);
			
			outputStream.write(Integer.toString(quotesList.size()).getBytes());
			outputStream.write(LINE_TERMINATOR.getBytes());
			
			
			for(int i = 0; i < quotesList.size(); i++){
				Quote q = quotesList.get(i);
				
				outputStream.write(q.getAuthor().getBytes());
				outputStream.write(LINE_TERMINATOR.getBytes());
				
				outputStream.write(Integer.toString(q.getYear()).getBytes());
				outputStream.write(LINE_TERMINATOR.getBytes());
				
				outputStream.write(q.getQuote().getBytes());
				outputStream.write(LINE_TERMINATOR.getBytes());
				
				outputStream.write(QUOTE_TERMINATOR.getBytes());
				outputStream.write(LINE_TERMINATOR.getBytes());
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void saveList(){
		writeQuoteList();
	}
	public Quote getQuote(int position){
		return quotesList.get(position);
	}
	
	public void setQuote(Quote q){
		quotesList.add(q);
	}
	public void setQuote(int position, Quote q){
		quotesList.set(position, q);
	}
	
	public void deleteQuote(int position){
		quotesList.remove(position);
	}
	
}
