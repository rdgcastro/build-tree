import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

class Reference {
    private String current;
    private List<String> path;

    public Reference() {
    };

    public Reference(String current, List<String> path) {
        this.current = current;
        this.path = path;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public List<String> getPath() {
        return path;
    }

    public void setPath(List<String> path) {
        this.path = path;
    }

}

class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        // Load file
        List<List<String>> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("CONJUNTOS.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String l = line.replaceAll("\"", "");
                String[] value = l.split(",");

                list.add(Arrays.asList(value));
            }
        }

        Reference ref = new Reference("00130160", new ArrayList<>());

        List<Reference> stack = new ArrayList<>();
        List<List<String>> tree = new ArrayList<>();
        stack.add(ref);

        while (stack.size() > 0) {
            Reference cur = stack.removeLast();

            List<Reference> founded = new ArrayList<>();

            for (int i = 0; i < list.size(); i++) {
                List<String> row = list.get(i);
                if (row.get(0).equals(cur.getCurrent())) {
                    if (cur.getPath().size() == 0) {
                        List<String> l = new ArrayList<>();
                        l.add(cur.getCurrent());
                        l.add(row.get(1));
                        Reference re = new Reference(row.get(1), l);
                        founded.add(re);
                    } else {
                        List<String> l = new ArrayList<>();
                        l.addAll(cur.getPath());
                        l.add(row.get(1));
                        Reference re = new Reference(row.get(1), l);
                        founded.add(re);
                    }
                }
            }

            if (founded.size() > 0) {

                for (int i = 0; i < founded.size(); i++) {
                    StringBuilder paths = new StringBuilder();
                    Reference r = founded.get(i);
                    List<String> ePath = r.getPath();
                    for (int j = 0; j < ePath.size(); j++) {
                        paths.append(ePath.get(j));
                        if (j < ePath.size() - 1) {
                            paths.append("-");
                        }
                    }
                    List<String> tRow = new ArrayList<>();
                    tRow.add(founded.get(i).getCurrent());
                    tRow.add(paths.toString());
                    tree.add(tRow);
                }
                stack.addAll(founded);
            }
        }

        tree.sort(Comparator.comparing(s -> s.get(1)));
        tree.forEach(System.out::println);
    }
}