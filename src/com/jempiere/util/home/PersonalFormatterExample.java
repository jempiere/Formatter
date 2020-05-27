package com.jempiere.util.home;

public class PersonalFormatterExample extends EFormatter{
    private String[] myFormats = {"someArgs(","noArgs()"};
    @Override
    public String format(String data, String format, Void personal) {
        lastCall = data;
        if(format.contains(myFormats[0])){
            String parameterStorage = extrapolate(format, myFormats[0]);
            //Do something with parameterStorage and edit the original data String here :)
        }
        if(format.contains(myFormats[1])){
            //Because there is no arguments, just do the intended behavior here :) don't extrapolate unnecessarily
            data = data.toString(); //just do something with it! no extrapolation required :)
        }
        lastResult = data;
        return data;
    }
    public String[] getFormats(boolean isPersonal){
        if(isPersonal)
            return myFormats;
        return super.getFormats();
    }
}
