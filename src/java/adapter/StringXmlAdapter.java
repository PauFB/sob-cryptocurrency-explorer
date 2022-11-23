package adapter;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class StringXmlAdapter extends XmlAdapter<String, String> {
    
    @Override
    public String marshal(String string) throws Exception {
        return null;
    }

    @Override
    public String unmarshal(String string) throws Exception {
        return string;
    }
    
}
