// Created on 27.10.2004
package de.mp3db.util;

/**     Klasse repräsentiert ein MP3-File
 *      
 * 		Original in c-Quellcode gefunden in dem Magazin c't 4/97
 * 		Artikelname 'Text-Detektor' von Dr. Reinhard Rapp
 * 
 *      @author $Author: einfachnuralex $
 *
 *      @version $Id: FuzzySearch.java,v 1.2 2004/10/29 11:00:27 einfachnuralex Exp $
 *  
 *      $Log: FuzzySearch.java,v $
 *      Revision 1.2  2004/10/29 11:00:27  einfachnuralex
 *      getResult Methode hinzugefügt
 *
 *      Revision 1.1  2004/10/28 14:32:11  einfachnuralex
 *      Add to CVS
 *
 * 
 */
public class FuzzySearch {
	private int MaxParLen = 1000;
	private double   Similarity = 0.0;
	private int maxMatch;	
	private int min;
	
	public FuzzySearch(String searchBuffer, String searchString, int threshold) {
		char[] textPara = new char[MaxParLen];
		char[] searchStr = new char[searchString.length()];
		int 	textLen, searchStrLen;
		int    NGram1Len, NGram2Len;
		int    MatchCount1, MatchCount2;
		int    MaxMatch1 = 0; 
		int 	MaxMatch2 = 0;
		
		min = threshold;
		searchStr = prepareString(searchString);
		searchStrLen = searchString.length();
		
		NGram1Len = 3;
		//NGram2Len = (searchStrLen < 7) ? 2 : 5;
		if(searchStrLen < 7) NGram2Len = 2;
		else NGram2Len = 5;
		
		textPara = prepareString(searchBuffer);
		textLen = textPara.length;
		
		MatchCount1 = NGramMatch(textPara, searchStr, searchStrLen, NGram1Len);
		MaxMatch1 = maxMatch;
		MatchCount2 = NGramMatch(textPara, searchStr, searchStrLen, NGram2Len);
		MaxMatch2 = maxMatch;
		
        Similarity = 100.0 * (double)(MatchCount1 + MatchCount2) / (double)(MaxMatch1 + MaxMatch2);
		
        if(Similarity > threshold) {
        	System.out.println(searchString + ": " + Similarity + "  " + String.valueOf(textPara));
        }
	}
	
	public double getResult() {
		if(Similarity > min) return Similarity;
		else	return 0.0;
	}
	
	private int NGramMatch(char[] textPara, char[] searchString, int searchStrLen, int NGramLen) {
		char[] NGram = new char[NGramLen];
		int     NGramCount;
		int     i = 0; 
		int Count = 0;
		
		maxMatch = 0;

		//NGram[NGramLen] = '\0';
		NGramCount = searchStrLen - NGramLen + 1;

		for(i = 0; i < NGramCount; i++) {
			//memcpy(NGram, &SearchStr[i], NGramLen);
			for(int k = 0; k<NGramLen; k++) {
				NGram[k] = searchString[i+k];
			}

			if (NGram[NGramLen - 2] == ' ' && NGram[0] != ' ')
				i += NGramLen - 3;
			else {
				maxMatch  += NGramLen;
				if(String.valueOf(textPara).indexOf(String.valueOf(NGram)) != -1) Count++;

			}
		}
		return Count * NGramLen;  /* gewichten nach n-Gramm-Laenge */
	}

	private char[] prepareString(String originalString) {
		 char[] tmpStr = new char[originalString.length()];
		
		originalString.toLowerCase();
		
		for(int i = 0; i < originalString.length(); i++) {
			char tmp = originalString.charAt(i);
			
	        switch(tmp) {
				case 196: tmp = 228; break; /* ANSI-Umlaute  */
				case 214: tmp = 246; break;
				case 220: tmp = 252; break;
				case 142: tmp = 132; break; /* ASCII-Umlaute */
				case 153: tmp = 148; break;
				case 154: tmp = 129; break;
				case ':': tmp = ' '; break;
				case ';': tmp = ' '; break;
				case '<': tmp = ' '; break;
				case '>': tmp = ' '; break;
				case '=': tmp = ' '; break;
				case '?': tmp = ' '; break;
				case '[': tmp = ' '; break;
				case ']': tmp = ' '; break;

	        }
	        tmpStr[i] = tmp;
			
		}
		return tmpStr;
	}
}
