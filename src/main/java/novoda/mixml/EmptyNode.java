
package novoda.mixml;


public class EmptyNode extends XMLNode {
    public XMLNode get(int index) {
        return this;
    }

    public XMLNode path(String name) {
        return this;
    }

    public String getAsString() {
        return "";
    }
}
