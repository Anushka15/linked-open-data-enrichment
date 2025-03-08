package org.utwente.cs.ds.semi.lod;

import org.utwente.cs.ds.semi.lod.arxiv.service.ArxivDataToRdfConversionService;
import org.utwente.cs.ds.semi.lod.dblp.model.Hits;
import org.utwente.cs.ds.semi.lod.dblp.service.DblpDataToRdfConversionService;
import org.utwente.cs.ds.semi.lod.ieee.service.IeeeDataToRdfConversionService;

import java.util.concurrent.atomic.AtomicLong;

public class Main {

    public static void main(String[] rags)  {

        AtomicLong publicationCount = new AtomicLong(1);
        AtomicLong authorCount = new AtomicLong(1);

        //DblpDataToRdfConversionService.convertDblpAPIResponseToRDF(DblpDataToRdfConversionService.getDblpDataFromApi("Data%20Science"),"dblp.nt",publicationCount,authorCount);


        AtomicLong publicationCountArxiv = new AtomicLong(1);
        AtomicLong authorCountArxiv = new AtomicLong(1);
        //ArxivDataToRdfConversionService.convertArxivAPIResponseToRDF(ArxivDataToRdfConversionService.getArxivDataFromApi("Data%20Science"),"2025/scientificPublicationsArxiv.nt",publicationCountArxiv,authorCountArxiv);
        //ArxivDataToRdfConversionService.convertArxivAPIResponseToRDF(ArxivDataToRdfConversionService.getArxivDataFromApi("Linked%20Open%20Data"),"2025/scientificPublicationsArxiv.nt",publicationCountArxiv,authorCountArxiv);
        ArxivDataToRdfConversionService.convertArxivAPIResponseToRDF(ArxivDataToRdfConversionService.getArxivDataFromApi("Computer%20Science"),"2025/scientificPublicationsArxiv.nt",publicationCountArxiv,authorCountArxiv);

        AtomicLong publicationCountIeee = new AtomicLong(1);
        AtomicLong authorCountIeee = new AtomicLong(1);
        AtomicLong isbnCountIeee = new AtomicLong(1);
        //IeeeDataToRdfConversionService.convertIeeeAPIResponseToRDF(IeeeDataToRdfConversionService.getIeeeDataFromApi("Data%20Science"),"2025/scientificPublicationsIEEE.nt",publicationCountIeee,authorCountIeee,isbnCountIeee);
        //IeeeDataToRdfConversionService.convertIeeeAPIResponseToRDF(IeeeDataToRdfConversionService.getIeeeDataFromApi("Linked%20Open%20Data"),"2025/scientificPublicationsIEEE.nt",publicationCountIeee,authorCountIeee,isbnCountIeee);
        IeeeDataToRdfConversionService.convertIeeeAPIResponseToRDF(IeeeDataToRdfConversionService.getIeeeDataFromApi("Computer%20Science"),"2025/scientificPublicationsIEEE.nt",publicationCountIeee,authorCountIeee,isbnCountIeee);

//        AtomicLong publicationCountCombined = new AtomicLong(1);
//        AtomicLong authorCountCombined = new AtomicLong(1);
//        AtomicLong isbnCountCombined = new AtomicLong(1);
//        ArxivDataToRdfConversionService.convertArxivAPIResponseToRDF(ArxivDataToRdfConversionService.getArxivDataFromApi("Data%20Science"),"2025/scientificPublicationsCombined.nt",publicationCountCombined,authorCountCombined);
//        ArxivDataToRdfConversionService.convertArxivAPIResponseToRDF(ArxivDataToRdfConversionService.getArxivDataFromApi("Linked%20Open%20Data"),"2025/scientificPublicationsCombined.nt",publicationCountCombined,authorCountCombined);
//        IeeeDataToRdfConversionService.convertIeeeAPIResponseToRDF(IeeeDataToRdfConversionService.getIeeeDataFromApi("Data%20Science"),"2025/scientificPublicationsCombined.nt",publicationCountCombined,authorCountCombined,isbnCountCombined);
//        IeeeDataToRdfConversionService.convertIeeeAPIResponseToRDF(IeeeDataToRdfConversionService.getIeeeDataFromApi("Linked%20Open%20Data"),"2025/scientificPublicationsCombined.nt",publicationCountCombined,authorCountCombined,isbnCountCombined);

        System.out.println("Publication Count IEEE: "+publicationCountIeee.get());
        System.out.println("Publication Count Arxiv: "+publicationCountArxiv.get());
        //System.out.println("Publication Count Combined: "+publicationCountCombined.get());

    }
}
