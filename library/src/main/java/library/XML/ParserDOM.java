package library.XML;

import library.model.Author;
import library.model.Book;
import library.model.Genre;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ParserDOM {

    private static Document document;

    public ArrayList<Book> getBooks(File file) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        document = documentBuilder.parse(file);

        NodeList bookNodeList = document.getElementsByTagName("book");

        ArrayList<Book> bookList = new ArrayList<>();

        for (int i = 0; i < bookNodeList.getLength(); i++) {
            Element bookElement = (Element) bookNodeList.item(i);

            Book book = new Book();

            NodeList nodes = bookElement.getChildNodes();
            for (int j = 0; j < nodes.getLength(); j++) {
                if (nodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nodes.item(j);

                    switch (element.getNodeName()) {
                        case "title":
                            book.setTitle(element.getTextContent());
                            break;
                        case "author":
                            book.setAuthor(new Author(element.getTextContent()));
                            break;
                        case "genre":
                            String genre = element.getTextContent().trim();
                            book.setGenre(Genre.valueOf(genre));
                            break;
                        case "isbn":
                            book.setISBN(element.getTextContent());
                            break;
                    }
                }
            }
            bookList.add(book);
        }
        return bookList;
    }
}
