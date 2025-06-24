package com.recceda.invoice;

import com.recceda.invoice.impl.ReccedaInvoice;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        ReccedaInvoice invoice = new ReccedaInvoice();
        invoice.generateInvoice();
    }
}
