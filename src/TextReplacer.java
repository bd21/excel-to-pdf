import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class TextReplacer {
    private String searchValue;
    private String replacement;

    public TextReplacer(String searchValue, String replacement) {
        this.searchValue = searchValue;
        this.replacement = replacement;
    }

    public void replace(XWPFDocument document) {
        List<XWPFParagraph> paragraphs = document.getParagraphs();
	
	    for (XWPFParagraph xwpfParagraph : paragraphs) {
	        replace(xwpfParagraph);
	    }
	}

	private void replace(XWPFParagraph paragraph) {
	    if (hasReplaceableItem(paragraph.getText())) {
	        String replacedText = StringUtils.replace(paragraph.getText(), searchValue, replacement);
	
	        removeAllRuns(paragraph);
	
	        insertReplacementRuns(paragraph, replacedText);
	    }
	}

	private void insertReplacementRuns(XWPFParagraph paragraph, String replacedText) {
	    String[] replacementTextSplitOnCarriageReturn = StringUtils.split(replacedText, "\n");
	
	    for (int j = 0; j < replacementTextSplitOnCarriageReturn.length; j++) {
	        String part = replacementTextSplitOnCarriageReturn[j];
	
	        XWPFRun newRun = paragraph.insertNewRun(j);
	        newRun.setText(part);
	
	        if (j+1 < replacementTextSplitOnCarriageReturn.length) {
	            newRun.addCarriageReturn();
	        }
	    }       
	}

	private void removeAllRuns(XWPFParagraph paragraph) {
	    int size = paragraph.getRuns().size();
	    for (int i = 0; i < size; i++) {
	        paragraph.removeRun(0);
	    }
	}

	private boolean hasReplaceableItem(String runText) {
	    return StringUtils.contains(runText, searchValue);
	}
}

//REVISIT The below can be removed if Michele tests and approved the above less versatile replacement version

//  private void replace(XWPFParagraph paragraph) {
//      for (int i = 0; i < paragraph.getRuns().size()  ; i++) {
//          i = replace(paragraph, i);
//      }
//  }

//  private int replace(XWPFParagraph paragraph, int i) {
//      XWPFRun run = paragraph.getRuns().get(i);
//      
//      String runText = run.getText(0);
//      
//      if (hasReplaceableItem(runText)) {
//          return replace(paragraph, i, run);
//      }
//      
//      return i;
//  }

//  private int replace(XWPFParagraph paragraph, int i, XWPFRun run) {
//      String runText = run.getCTR().getTArray(0).getStringValue();
//      
//      String beforeSuperLong = StringUtils.substring(runText, 0, runText.indexOf(searchValue));
//      
//      String[] replacementTextSplitOnCarriageReturn = StringUtils.split(replacement, "\n");
//      
//      String afterSuperLong = StringUtils.substring(runText, runText.indexOf(searchValue) + searchValue.length());
//      
//      Counter counter = new Counter(i);
//      
//      insertNewRun(paragraph, run, counter, beforeSuperLong);
//      
//      for (int j = 0; j < replacementTextSplitOnCarriageReturn.length; j++) {
//          String part = replacementTextSplitOnCarriageReturn[j];
//
//          XWPFRun newRun = insertNewRun(paragraph, run, counter, part);
//          
//          if (j+1 < replacementTextSplitOnCarriageReturn.length) {
//              newRun.addCarriageReturn();
//          }
//      }
//      
//      insertNewRun(paragraph, run, counter, afterSuperLong);
//      
//      paragraph.removeRun(counter.getCount());
//      
//      return counter.getCount();
//  }

//  private class Counter {
//      private int i;
//      
//      public Counter(int i) {
//          this.i = i;
//      }
//      
//      public void increment() {
//          i++;
//      }
//      
//      public int getCount() {
//          return i;
//      }
//  }

//  private XWPFRun insertNewRun(XWPFParagraph xwpfParagraph, XWPFRun run, Counter counter, String newText) {
//      XWPFRun newRun = xwpfParagraph.insertNewRun(counter.i);
//      newRun.getCTR().set(run.getCTR());
//      newRun.getCTR().getTArray(0).setStringValue(newText);
//      
//      counter.increment();
//      
//      return newRun;
//  }