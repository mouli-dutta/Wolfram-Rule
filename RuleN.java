package mouli;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RuleN {
    public static void main(String[] args) {
        int generation = 25;

        try(var sc = new Scanner(System.in)) {

            int rule = sc.nextInt();
            String s = "Wolfram's MathWorld Cellular Automata Rule " + rule;
            System.out.println(s.indent(10));

            printPattern(rule, generation);

        } catch (Exception e) {
            System.err.println("Bad Input. Using default values.");
            int[] rules = {30, 54, 57, 60, 77, 79, 90, 94, 110, 122, 126, 150, 182, 190};

            for (int rule: rules) {
                System.out.println("Rule " + rule);
                printPattern(rule, generation);

                System.out.println("\n\n");
            }
        }
    }

    private static String getState(String neighbour, int rule) {
        return (rule & (1 << Integer.parseInt(neighbour, 2))) != 0 ? "1" : "0";
    }

    private static String ruleN(String initialState, int rule) {
        String[] neighbours = {"111", "110", "101", "100", "011", "010", "001", "000"};

        HashMap<String, String> rules = Arrays.stream(neighbours)
                .collect(Collectors.toMap(
                        neighbour -> neighbour,
                        neighbour -> getState(neighbour, rule),
                        (a, b) -> b, HashMap::new)
                );

        return IntStream.range(0, initialState.length() - 2)
                .mapToObj(i -> initialState.substring(i, i+3))
                .map(rules::get)
                .collect(Collectors.joining());
    }

    private static List<String> generateList(String initial, int rule, int gen) {
        ArrayList<String> list = new ArrayList<>();
        list.add(getColor(initial));

        for (int i = 0; i < gen; i++) {
            initial = ruleN("0%s0".formatted(initial), rule);
            list.add(getColor(initial));
        }
        return list;
    }

    private static void printPattern(int rule, int gen) {
        int n = gen << 1 | 1;
        var sb = new StringBuilder("0".repeat(n)).replace(n >> 1, n >> 1 | 1, "1");
        generateList(sb.toString(), rule, gen).forEach(System.out::println);
    }

    private static String getColor(String a) {
        return a.replaceAll("0", ".").replaceAll("1", "%");
    }
}

