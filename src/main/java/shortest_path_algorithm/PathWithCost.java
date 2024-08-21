package shortest_path_algorithm;

import java.util.List;

class PathWithCost {
    List<String> path;
    int cost;

    PathWithCost(List<String> path, int cost) {
        this.path = path;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Path: " + path + ", Total Cost: " + cost;
    }
}