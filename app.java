import java.util.ArrayList;
import java.util.List;
import java.lang.Math;
public class App {
//To get combinations
    public static List<List<String>> getCombinations(String[] Links_data) {
        List<List<String>> allCombinations = new ArrayList<>(); int numLinks = Links_data.length;
        for (int r = 1; r <= numLinks; r++) {
            generateCombinations(Links_data, r, 0, new ArrayList<>(), allCombinations); }
            return allCombinations;
        }
//To generate combinations
    public static void generateCombinations(String[] Links_data, int r, int start, List<String> currentCombination, List<List<String>> allCombinations) {
        if (r == 0) {
            allCombinations.add(new ArrayList<>(currentCombination));
            return;
        }
        int numLinks = Links_data.length;
        for (int i = start; i <= numLinks - r; i++) {
            currentCombination.add(Links_data[i]);
            generateCombinations(Links_data, r - 1, i + 1, currentCombination, allCombinations); currentCombination.remove(currentCombination.size() - 1);
        }
    }
//To get adjacency matrix values deleted
    public static int[][] getAdjacencyMatrix(int[][] temp, String link){
        switch (link) { 
            case "a":temp[0][1]=0; temp[1][0]=0; break;
            case "b": temp[0][2]=0; temp[2][0]=0;break;
            case "c":temp[0][5]=0; temp[5][0]=0; break;
            case "d": temp[1][3]=0; temp[3][1]=0; break;
            case "e": temp[3][4]=0; temp[4][3]=0; break;
            case "f": temp[1][2]=0; temp[2][1]=0; break;
            case "g": temp[2][5]=0; temp[5][2]=0; break;
            case "h": temp[5][6]=0; temp[6][5]=0; break;
            case "i": temp[4][6]=0; temp[6][4]=0; break;
            case "j": temp[2][4]=0; temp[4][2]=0; break;
        }
        return temp;
     }
    public static boolean isGraphConnected(int[][] adjacencyMatrix) { 
        int numNodes = adjacencyMatrix.length;
        boolean[] visited = new boolean[numNodes];
// Start the DFS from the first node (node 0) 
        dfs(adjacencyMatrix, 0, visited);
// Check if all nodes were visited 
        for (boolean isVisited : visited) {
            if (!isVisited) {
                return false; }
            }
        return true;
     }
    public static void dfs(int[][] adjacencyMatrix, int node, boolean[] visited) { visited[node] = true;
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if (adjacencyMatrix[node][i] == 1 && !visited[i]) {
                dfs(adjacencyMatrix, i, visited); }
        } }
//To check connectivity
    public static double network_reliability( double p, int[][] adj_matrix, List<List<String>> combinations){
//Intializing with all values active double reliability = Math.pow(p,10); double probability=p;
        int[][] matrix = new int[7][7];
        int[][] temp = new int[7][7];
    for(int x=0; x< adj_matrix.length; x++){
for(int y=0;y<adj_matrix.length;y++){ temp[x][y]=adj_matrix[x][y];
matrix[x][y]=adj_matrix[x][y]; }
}
        List<Integer> up_size = new ArrayList<>();
//Links that are up
        List<List<String>> up_combinations = new ArrayList<>(); 
        for( int i=0; i<combinations.size();i++){
            for(int x=0; x< matrix.length; x++){ 
                for(int y=0;y<matrix.length;y++){
                   temp[x][y]=matrix[x][y]; 
                }
            }
        List<String> Curr_data = combinations.get(i);
        for (int j = 0; j < Curr_data.size(); j++){ 
            String item = Curr_data.get(j);
            temp=getAdjacencyMatrix(temp, item); 
        }
//check for connectivity
        if(isGraphConnected(temp)== true){ 
            up_combinations.add(combinations.get(i));
            up_size.add(combinations.get(i).size()); }
        }
//up_size has number of links that are disconnected that is probability will be (1-p) for(int i=0;i<up_size.size();i++){
        int size_data =up_size.get(i);
        reliability += (Math.pow((probability),(10- size_data))*Math.pow((1-p),(size_data)));
    }
//check reliability return reliability;
    }
    public static void main(String[] args) throws Exception {
        int[][] adj_matrix = {{0,1,1,0,0,1,0},{1,0,1,1,0,0,0},{1,1,0,0,1,1,0},{0,1,0,0,1,0,0} ,{0,0,1,1,0,0,1},{1,0,1,0,0,0,1},{0,0,0,0,1,1,0}};
        double p = 0.05;
//Indicating each link as a alphabet
//a--> Link between 1 and 2
//b--> Link between 1 and 3
//c--> Link between 1 and 6
//d--> Link between 2 and 4
//e--> Link between 4 and 5
//f--> Link between 2 and 3
//g--> Link between 3 and 6
//h--> Link between 6 and 7
//i--> Link between 5 and 7
//j--> Link between 3 and 5
        String[] Links_data = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};
        List<List<String>> combinations = getCombinations(Links_data); List<Double> Reliabilities = new ArrayList<>();
        while(p<1.05){
            double reliability_scale = network_reliability(p,adj_matrix,combinations); System.out.printf("The Reliability is %f for the value of p %.2f%n", reliability_scale, p); Reliabilities.add(reliability_scale);
            p=p+0.05;
        }

 
} }
