package test.automator.control.xml.parse;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

/**
 * This class parse in XML soap request and soap response and fetch all the non empty nodes from XML file
 * It also removes repeated node names (Provided removeRepeatedNodes is set to true),
 * else it will fetch the node names with its unique parent node name
 * 
 * 
 * @author kamalesh.p
 * 
 */
public class ParseXML {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void parseRequest(final String reqXml, final boolean removeRepeatedNodes) throws Exception {
        if (removeRepeatedNodes == true) {
            EditInputExcel.writeRequest(parseRemoveRepeatedNodes(reqXml));
        } else {
            EditInputExcel.writeRequest(parse(reqXml));
        }

    }

    public static void parseResponse(final String resXml, final boolean removeRepeatedNodes) throws Exception {
        if (removeRepeatedNodes == true) {
            EditInputExcel.writeResponse(parseRemoveRepeatedNodes(resXml));
        } else {
            EditInputExcel.writeResponse(parse(resXml));
        }

    }

    static Document     doc  = null;
    static List<String> list = new ArrayList<String>();
    static NodeList     nodeList;

    private static boolean isHeaderProperty(final String arg) {
        if (arg.equalsIgnoreCase("StrErrorCode") || arg.equalsIgnoreCase("NativeError") || arg.equalsIgnoreCase("LogSequence") || arg.equalsIgnoreCase("SourceApplication")
                || arg.equalsIgnoreCase("DestinationApplication") || arg.equalsIgnoreCase("Function") || arg.equalsIgnoreCase("Version") || arg.equalsIgnoreCase("userid")
                || arg.equalsIgnoreCase("credentials") || arg.equalsIgnoreCase("channel") || arg.equalsIgnoreCase("orgid") || arg.equalsIgnoreCase("locale")
                || arg.equalsIgnoreCase("orgunit") || arg.equalsIgnoreCase("customerid") || arg.equalsIgnoreCase("ip")) {
            return true;
        }
        return false;
    }

    private static int getNodeIndex(final String arg) {
        return list.indexOf(arg);
    }

    // private String getList(final List<String> list) {
    // return list.toString().replace("[", "").replace("]", "").replace(", ", "\n");
    // }

    private static int getArgumentIndex(final String arg) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            // fetch non-empty nodes
            if (null != (null != nodeList.item(i).getFirstChild() ? null != nodeList.item(i).getFirstChild().getNodeValue()
                    && !nodeList.item(i).getFirstChild().getNodeValue().trim().isEmpty() ? nodeList.item(i).getFirstChild()
                    .getNodeValue().trim() : null : null)) {
                String[] argumentValue = nodeList.item(i).getNodeName().split(":");
                // removes node name space details
                String argumentName = argumentValue.length > 1 ? argumentValue[1].toString() : argumentValue[0].toString();
                if (argumentName.equals(arg)) {
                    return i;
                }
            }
        }
        return -1;
    }

    private static int Count = 0;

    private static boolean isUniqueNode(final Node parentNode) {
        String[] inParentNode = parentNode.getNodeName().split(":");
        String inParentNodeName = inParentNode.length > 1 ? inParentNode[1] : inParentNode[0];
        Count = 0;
        for (int i = 0; i < nodeList.getLength(); i++) {
            // fetch all nodes
            if (null != nodeList.item(i).getFirstChild()) {
                String[] nodeNameFromList = nodeList.item(i).getNodeName().split(":");
                // removes node name space details
                String nodeNameFromListWithoutNamespace = nodeNameFromList.length > 1 ? nodeNameFromList[1].toString() : nodeNameFromList[0].toString();
                if (nodeNameFromListWithoutNamespace.equals(inParentNodeName)) {
                    Count++;
                }
            }
        }
        // if the given xml contains only one node of this name return true
        return 1 == Count;
    }

    private static Node getParentNode(final Node inNode) {
        return inNode.getParentNode();
    }

    /**
     * convert XML(SOAP message) into property list of non empty nodes
     * 
     * @param args
     * @throws Exception
     */
    private static List<String> parse(final String xml) throws Exception {
        doc = null;
        list = new ArrayList<String>();
        List<String> outputList = new ArrayList<String>();
        nodeList = null;

        try {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
        } catch (SAXParseException E) {
            JOptionPane.showMessageDialog(null, "Plese provide XML!\nGiven Content Unable to Parse");
            throw new Exception("XML Parse Exception");
        }
        nodeList = doc.getElementsByTagName("*");

        // loop to fetch non empty node name without namespace.
        for (int i = 0; i < nodeList.getLength(); i++)
        {
            if (null != (null != nodeList.item(i).getFirstChild() ? null != nodeList.item(i).getFirstChild().getNodeValue()
                    && !nodeList.item(i).getFirstChild().getNodeValue().trim().isEmpty() ? nodeList.item(i).getFirstChild()
                    .getNodeValue().trim() : null : null)) {
                LOGGER.info("NodeName: " + nodeList.item(i).getNodeName() + " >>> " + "NodeValue: " + nodeList.item(i).getFirstChild().getNodeValue());
                String[] ary = nodeList.item(i).getNodeName().split(":");
                String arrayValue = ary.length > 1 ? ary[1].toString() : ary[0].toString();
                /*
                 * customized to remove some properties,
                 */
                // if argumentName is in header list don't add to output list
                if (!isHeaderProperty(arrayValue)) {
                    /*
                     * if argumentName does not existed in list, add it to list(reference list) and outputList
                     * else get parent node of particular argumentName/nodeName and append it
                     */
                    if (!list.contains(arrayValue)) {
                        list.add(arrayValue);
                        outputList.add(arrayValue);
                    } else {
                        //
                        int existingNodeIndexInArray = getNodeIndex(arrayValue);
                        int existingNodeIndex = getArgumentIndex(arrayValue);
                        // to edit the existing node name
                        Node existingNodeParentNode = nodeList.item(existingNodeIndex).getParentNode();
                        String[] existingNodeParentNodeName = existingNodeParentNode.getNodeName().split(":");
                        String existingNodeParentNodeNameWithoutNamespace = existingNodeParentNodeName.length > 1 ? existingNodeParentNodeName[1]
                                : existingNodeParentNodeName[0];
                        String nodeNameAppended = existingNodeParentNodeNameWithoutNamespace;
                        // chech if the given node is unique or not
                        while (!isUniqueNode(existingNodeParentNode)) {
                            Node newExistingNodeParentNode = getParentNode(existingNodeParentNode);
                            String[] newExistingNodeParentNodeName = newExistingNodeParentNode.getNodeName().split(":");
                            String newExistingNodeParentNodeNameWithoutNamespace = newExistingNodeParentNodeName.length > 1 ? newExistingNodeParentNodeName[1]
                                    : newExistingNodeParentNodeName[0];
                            nodeNameAppended = newExistingNodeParentNodeNameWithoutNamespace + "/*:" + nodeNameAppended;
                            existingNodeParentNode = newExistingNodeParentNode;
                        }

                        String existingNodeXpath = nodeNameAppended + "/*:" + ary[1];
                        // edit output list with appended parent node name for existing nodes.
                        outputList.set(existingNodeIndexInArray, existingNodeXpath);
                        // set new node name with parent node value
                        Node parentNode = nodeList.item(i).getParentNode();
                        String[] parentNodeName = parentNode.getNodeName().split(":");
                        String parentNodeNameWithoutNamespace = parentNodeName.length > 1 ? parentNodeName[1] : parentNodeName[0];
                        String newNodeNameAppended = parentNodeNameWithoutNamespace;
                        // Check if the given node is unique or not
                        while (!isUniqueNode(parentNode)) {
                            Node newParentNode = getParentNode(parentNode);
                            String[] newParentNodeName = newParentNode.getNodeName().split(":");
                            String newParentNodeNameWithoutNamespace = newParentNodeName.length > 1 ? newParentNodeName[1]
                                    : newParentNodeName[0];
                            newNodeNameAppended = newParentNodeNameWithoutNamespace + "/*:" + newNodeNameAppended;
                            parentNode = newParentNode;
                        }

                        String newNodeXpath = newNodeNameAppended + "/*:" + ary[1];

                        // add new node to both list(reference list) and outputList
                        list.add(newNodeXpath);
                        outputList.add(newNodeXpath);
                    }
                }
            }
        }
        System.out.println(outputList);
        System.out.println(list);
        System.out.println("OutputListLength:" + outputList.size());
        System.out.println("ReferanceListLength:" + list.size());
        return outputList;
    }

    /**
     * convert XML(SOAP message) into property list of non empty nodes
     * Also removes repeated properties.
     * 
     * @param args
     * @throws Exception
     */
    private static List<String> parseRemoveRepeatedNodes(final String xml) throws Exception {
        /*
         * Dialog for input xml
         */
        doc = null;
        nodeList = null;
        try {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
        } catch (SAXParseException E) {
            JOptionPane.showMessageDialog(null, "Plese provide XML!\nGiven Content Unable to Parse");
            throw new Exception("XML Parse Exception");
        }
        nodeList = doc.getElementsByTagName("*");
        list = new ArrayList<String>();
        /*
         * loop to fetch non empty node name without namespace.
         */
        for (int i = 0; i < nodeList.getLength(); i++)
        {

            if (null != (null != nodeList.item(i).getFirstChild() ? null != nodeList.item(i).getFirstChild().getNodeValue()
                    && !nodeList.item(i).getFirstChild().getNodeValue().trim().isEmpty() ? nodeList.item(i).getFirstChild()
                    .getNodeValue().trim() : null : null)) {
                LOGGER.info("NodeName: " + nodeList.item(i).getNodeName() + " >>> " + "NodeValue: " + nodeList.item(i).getFirstChild().getNodeValue());
                String[] ary = nodeList.item(i).getNodeName().split(":");
                String arrayValue = ary.length > 1 ? ary[1].toString() : ary[0].toString();
                /*
                 * customized to remove some properties,
                 * Also removes repeated properties.
                 */
                if (!isHeaderProperty(arrayValue)) {
                    if (!list.contains(arrayValue)) {
                        list.add(arrayValue);
                    }
                }
            }
        }
        System.out.println(list);
        return list;
    }
}
