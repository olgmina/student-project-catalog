package services.validate;

import javafx.scene.control.TextFormatter;

public class TextFormatNumber {

    public TextFormatter<String> getTextFormatterInteger(){
        return new TextFormatter<>(change -> {
            if (!change.isContentChange()) {
                return change;
            }
            String text = change.getControlNewText();
            if(text.equals("")){
                return change;
            }
            try{
                Integer.parseInt(text);
            }catch (Exception ex){
                return null;
            }
            return change;
        });
    }

    public TextFormatter<String> getTextFormatterFloat(){
        return new TextFormatter<>(change -> {
            if (!change.isContentChange()) {
                return change;
            }
            String text = change.getControlNewText();
            if(text.equals("")){
                return change;
            }
            String[] stopSymbols = new String[]{"f", "F", "d", "D"};
            for(String s: stopSymbols){
                if(text.contains(s)) {
                    return null;
                }
            }
            try{
                Float.parseFloat(text);
            }catch (Exception ex){
                return null;
            }
            return change;
        });
    }

}
