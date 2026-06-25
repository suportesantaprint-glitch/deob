package ASTNodes;

public class Number extends Literal {
    public final double value;

    public Number(String literalValue) {
        // Handle hexadecimal numbers in Lua format (0x or 0X)
        double parsedValue;
        String normalized = literalValue.toLowerCase();
        if (normalized.startsWith("0x")) {
            try {
                // Parse as long hex and convert to double
                parsedValue = (double) Long.parseLong(normalized.substring(2), 16);
            } catch (NumberFormatException e) {
                // Try parsing as hex float
                parsedValue = Double.parseDouble(normalized);
            }
        } else {
            parsedValue = Double.parseDouble(normalized);
        }
        value = parsedValue;
    }

    public Number(double literalValue) {
        value = literalValue;
    }

    @Override
    public String line() {
        return toString();
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    @Override
    public boolean matches(Node obj) {
        if (super.matches(obj)) {
            return value == ((Number)obj).value;
        }

        return false;
    }

    @Override
    public Number clone() {
        return new Number(value);
    }

    @Override
    public Node accept(IASTVisitor visitor) {
        return visitor.visit(this);
    }
}
