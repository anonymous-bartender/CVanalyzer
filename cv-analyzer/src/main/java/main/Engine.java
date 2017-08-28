package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import util.pdfCoverter;


/*
 * 
 * Author: Arghajit Bhattacharya
 * THis code help to find heatmap of characters and furthermore words present in a provided file.
 * Currently, Text and PDF are supported.
 * Check the stud.java to see its usage.
 * A frontend and few APIs are provided to make life easier. 
 * 
 */

public class Engine {

	private HashMap<Integer, Integer> charCount = new HashMap<Integer, Integer>();
	private HashMap<String, Integer> wordCount = new HashMap<String, Integer>();

	private String pathToFile = "";
	String pronouns = "i|me|my|mine|you|your|he|him|his|her|she|it|we|us|they|them|their";
	String verb = "is|am|are|was|were|been|have|has|be|shall|will";
	String preposition = "on|in|at|since|for|ago|before|to|till|by|under|below|over|from|into|onto|toward|of|about";

	public Engine(String pathToFile) {
		this.pathToFile = pathToFile;
	}

	public boolean isSupported() {
		return (getFileExtension(new File(pathToFile)).contains("pdf") ||
				getFileExtension(new File(pathToFile)).contains("txt"));
	}

	public void run() {

		try {

			if(getFileExtension(new File(pathToFile)).contains("pdf")) {

				//System.out.println("inside pdf");

				File text = pdfCoverter.convert(new File(pathToFile));
				BufferedReader br = new BufferedReader(new FileReader(text));
				for(String line; (line = br.readLine())!=null;) {
					String[] words = retrieveWords(line);
					for(String s:words) {

						if(s.equals("")) continue;

						if(!s.matches("[0-9]+$") || s.length() > 1) { //regex to find words
							if(wordCount.containsKey(s)) wordCount.replace(s, wordCount.get(s)+1); //existing
							else wordCount.put(s, 1);//new addition
						}

						for(int i=0;i<s.length();i++) {
							int ascii = (int) s.charAt(i);
							if(charCount.containsKey(ascii)) charCount.replace(ascii, charCount.get(ascii)+1); //existing
							else charCount.put(ascii, 1);//new addition
						}


					}
				}

				br.close();
			}
			else {

				//System.out.println("inside text");

				BufferedReader br = new BufferedReader(new FileReader(pathToFile));
				for(String line; (line = br.readLine())!=null;) {
					String[] words = retrieveWords(line);
					for(String s:words) {

						if(s.equals("")) continue;

						if(!s.matches("[0-9]+$") || s.length() > 1) {
							if(wordCount.containsKey(s)) wordCount.replace(s, wordCount.get(s)+1); //existing
							else wordCount.put(s, 1);//new addition
						}

						for(int i=0;i<s.length();i++) {
							int ascii = (int) s.charAt(i);
							if(charCount.containsKey(ascii)) charCount.replace(ascii, charCount.get(ascii)+1); //existing
							else charCount.put(ascii, 1);//new addition
						}


					}
				}
				br.close();
			}




		}catch(Exception e) {
			System.out.println("Error occured");
			e.printStackTrace();
			System.exit(-999);
		}


	}

	private String[] retrieveWords(String text) {
		String s = text.trim();
		s = s.replaceAll("[^a-zA-Z0-9]", ",");
		s = s.toLowerCase();
		return s.split(",");
	}

	private static String getFileExtension(File file) {
		String fileName = file.getName();
		if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
			String s =  fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
			//			System.out.println("format: "+s);
			return s;
		}
		else return "";
	}

	//Getters ->

	public String getChars() {

		String s = "";

		for(int key : charCount.keySet()) {
			s = (s.isEmpty()?("{\""+(char) key+"\":"+charCount.get(key)+"}"):(s + ",{\"" +(char) key+"\":"+charCount.get(key)+"}")); 
		}

		return "\"charset\":["+s+"]";
	}

	public String getWords() {

		String s = "";

		for(String key : wordCount.keySet()) {
			s = (s.isEmpty()?( "{\""+key+"\":"+wordCount.get(key)+"}"):(s + ",{\"" + key+"\":"+wordCount.get(key)+"}")); 
		}

		return "\"Wordset\":["+s+"]";
	}

	public String getVerbs() {

		String s = "";

		for(String key : wordCount.keySet()) {

			if(key.matches(verb)) {
				s = (s.isEmpty()?( "{\""+key+"\":"+wordCount.get(key)+"}"):(s + ",{\"" + key+"\":"+wordCount.get(key)+"}"));
			}
		}

		return "\"Verbs\":["+s+"]";
	}

	public String getPrepositions() {

		String s = "";

		for(String key : wordCount.keySet()) {

			if(key.matches(preposition)) {
				s = (s.isEmpty()?( "{\""+key+"\":"+wordCount.get(key)+"}"):(s + ",{\"" + key+"\":"+wordCount.get(key)+"}"));
			}
		}

		return "\"Prepositions\":["+s+"]";
	}

	public String getPronouns() {

		String s = "";

		for(String key : wordCount.keySet()) {

			if(key.matches(pronouns)) {
				s = (s.isEmpty()?( "{\""+key+"\":"+wordCount.get(key)+"}"):(s + ",{\"" + key+"\":"+wordCount.get(key)+"}"));
			}
		}

		return "\"Pronouns\":["+s+"]";
	}


	public String getWordCount() {

		String s = Integer.toString(wordCount.size());

		return s;
	}

	public String getTopPronoun() {

		String top_key="";
		int top_value=0;

		for(String key : wordCount.keySet()) {	
			if(key.matches(pronouns) && top_value<wordCount.get(key)) {
				top_key = key;
				top_value=wordCount.get(key);
			}
		}
		String s = "\"Pronoun\" : {"
				+ "\"Value\":\""+top_key+"\","
				+ "\"Count\":"+top_value
				+ "}";

		return s;
	}

	public String getTopVerb() {

		String top_key="";
		int top_value=0;

		for(String key : wordCount.keySet()) {	
			if(key.matches(verb) && top_value<wordCount.get(key)) {
				top_key = key;
				top_value=wordCount.get(key);
			}
		}
		String s = "\"Verb\" : {"
				+ "\"Value\":\""+top_key+"\","
				+ "\"Count\":"+top_value
				+ "}";

		return s;
	}

	public String getTopPreposition() {

		String top_key="";
		int top_value=0;

		for(String key : wordCount.keySet()) {	
			if(key.matches(preposition) && top_value<wordCount.get(key)) {
				top_key = key;
				top_value=wordCount.get(key);
			}
		}
		String s = "\"Preposition\" : {"
				+ "\"Value\":\""+top_key+"\","
				+ "\"Count\":"+top_value
				+ "}";

		return s;
	}

	public String getTopWord() {

		String top_key="";
		int top_value=0;

		for(String key : wordCount.keySet()) {	
			if(top_value<wordCount.get(key)) {
				top_key = key;
				top_value=wordCount.get(key);
			}
		}
		String s = "\"Word\" : {"
				+ "\"Value\":\""+top_key+"\","
				+ "\"Count\":"+top_value
				+ "}";

		return s;
	}


	public String getCharCount() {

		String s = Integer.toString(charCount.size());
		return s;
	}

	public String getAll () {

		String s="{"
				+ getChars()+","
				+ getWords()+","
				+ getVerbs()+","
				+ getPrepositions()+","
				+ "\"Top\":{"
				+ getTopPreposition()+","
				+ getTopPronoun() + ","
				+ getTopVerb() + ","
				+ getTopWord() + "}"
				+ "}";
		
		return s;
	}

}
