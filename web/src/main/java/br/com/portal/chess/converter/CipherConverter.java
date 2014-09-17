package br.com.portal.chess.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.portal.chess.utils.CipherUtils;

@FacesConverter("cipher")
public class CipherConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return value;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String cipher = "";
        try {
            cipher = CipherUtils.cipher(value.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return cipher;

    }
}