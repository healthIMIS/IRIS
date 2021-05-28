package iris.location_service.search.lucene;

import iris.location_service.dto.LocationInformation;
import iris.location_service.search.SearchIndex;
import iris.location_service.search.db.model.Location;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class LuceneIndexService implements SearchIndex {

    @Setter
    private Analyzer analyzer;

    @Getter
    private Directory dir;

    private LuceneSearcher luceneSearcher;

    @Setter
    @Autowired
    private LuceneIndexServiceProperties luceneIndexServiceProperties;

    @PostConstruct
    private void postConstruct() {
        try {
        analyzer = new StandardAnalyzer();

        // ToDo: This only works at development time.
        dir = FSDirectory.open(Paths.get(luceneIndexServiceProperties.getIndexDirectory()));

        luceneSearcher = new LuceneSearcher(dir, analyzer);
        }catch (IOException e){
            //ToDo proper exception handling
        }
    }

    @Override
    public List<LocationInformation> search(String keyword){
        try{

            // search
            List<Document> results = luceneSearcher.search(keyword);

            // parse to location list
            List<LocationInformation> locationList = new ArrayList<>();
            for(Document entry:results){
                locationList.add(createLocationInformation(entry));
            }
            return locationList;
        } catch (IOException | ParseException e) {
            log.error("Error while searching for [{}]: ", keyword, e);
            return new ArrayList<>();
        }
    }

    public void indexLocation(Location location) throws Exception {
        indexNewDocument(createDocument(location));
    }

    public void indexLocations(List<Location> locations){
        try {
            for(Location location:locations){
                // gibt es die Location bereits?
                // Zwei Anbieter benutzen die selbe id.
                // Provider A und B
                // Location: A -> ID: X
                // Location: B -> ID: X
                // See LocationIdentifier
                // if location does not exist
                indexNewDocument(createDocument(location));
                // if location exists updateDocument(createDocument(location));
            }
        }catch (Exception e){
            log.error("Error while indexLocations: ", e);
        }
    }

    private Document createDocument(Location location){
        Document doc = new Document();
        // Is the ID relevant for us?
        // doc.add(new TextField("name", location.getId().toString(), Field.Store.YES));
        // "publicKey" is not gettable
        doc.add(new TextField("Id", location.getId().getLocationId(), Field.Store.YES));
        doc.add(new TextField("ProviderId", location.getId().getProviderId(), Field.Store.YES));
        doc.add(new TextField("Name", location.getName(), Field.Store.YES));
        doc.add(new TextField("ContactOfficialName", location.getContactOfficialName(), Field.Store.YES));
        doc.add(new TextField("ContactRepresentative", location.getContactRepresentative(), Field.Store.YES));
        doc.add(new TextField("ContactAddressStreet", location.getContactAddressStreet(), Field.Store.YES));
        doc.add(new TextField("ContactAddressCity", location.getContactAddressCity(), Field.Store.YES));
        doc.add(new TextField("ContactAddressZip", location.getContactAddressZip(), Field.Store.YES));
        doc.add(new TextField("ContactOwnerEmail", location.getContactOwnerEmail(), Field.Store.YES));
        doc.add(new TextField("ContactEmail", location.getContactEmail(), Field.Store.YES));
        doc.add(new TextField("ContactPhone", location.getContactPhone(), Field.Store.YES));
        // I don't know what "contexts" are supposed to be, they are also not gettable.
        return doc;
    }

    private void indexNewDocument(Document doc) throws Exception {
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(dir, config);
        writer.addDocument(doc);
        writer.close();
    }

    private void indexExistingDocument(Document doc) throws Exception {
        // ToDo: Implement based on existing ID
    }

    private void deleteExistingDocument(Document doc) throws Exception {
        // ToDo: Implement based on existing ID
    }

    public LocationInformation createLocationInformation(Document document) {
        return new LocationInformation(); // TODO: 09.05.2021 add 'document to location information' parser
    }

    public void setDir(String path) throws IOException{
        this.dir = FSDirectory.open(Paths.get(path));
    }
}
