package application;

import org.xbill.DNS.Lookup;
import org.xbill.DNS.Record;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

/**
 * Created by Andreas on 5/19/16.
 */
public class MX_record {

    public static Record[] lookupMxRecords(final String domainPart) throws TextParseException {
        final Lookup dnsLookup = new Lookup(domainPart, Type.MX);
        return dnsLookup.run();
    }

    public static boolean validate(String mail) throws TextParseException{

        String domain = mail.substring(mail.indexOf("@") + 1);
        Record[] records = lookupMxRecords(domain);
        if (records == null || records.length == 0) {
                return false;
        }

        return true;
    }
}
