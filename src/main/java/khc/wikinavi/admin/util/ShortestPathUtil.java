package khc.wikinavi.admin.util;

import khc.wikinavi.admin.domain.Edge;
import khc.wikinavi.admin.domain.IndoorMap;
import khc.wikinavi.admin.domain.Vertex;

import java.util.*;

/**
 * Created by miki on 15. 11. 6..
 */
public class ShortestPathUtil {

    static class ShortestPathVertex implements Comparable<ShortestPathVertex> {
        Vertex vertex;
        int weightSum;
        List<Vertex> path = new ArrayList<>();

        public ShortestPathVertex(Vertex vertex) {
            this.vertex = vertex;
            weightSum = 0;
        }
        public ShortestPathVertex(Vertex vertex, int weightSum) {
            this.vertex = vertex;
            this.weightSum = weightSum;
        }

        @Override public int compareTo(ShortestPathVertex o) {
            return weightSum - o.weightSum;
        }
        @Override public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ShortestPathVertex that = (ShortestPathVertex) o;

            return vertex.equals(that.vertex);

        }
        @Override public int hashCode() {
            return vertex.hashCode();
        }
    }

    public static List<Vertex> shortestPath(IndoorMap indoorMap, Vertex start, Vertex end) {
        // 전체 정점
        Set<Vertex> vertexes = new HashSet<>();
        vertexes.addAll(indoorMap.getVertexes());

        // 최단 경로 정점
        Set<ShortestPathVertex> spVertexes = new HashSet<>();

        // 주변 정점
        TreeSet<ShortestPathVertex> surroundVertexes = new TreeSet<>();
        surroundVertexes.add(new ShortestPathVertex(start, 0));
        surroundVertexes.first().path.add(surroundVertexes.first().vertex);

        // 최단 경로 계산
        while (vertexes.size() > spVertexes.size()) {
            // 주변 정점에서 가장 가중치가 낮은 정점을 poll 하여 최단 경로 정점으로 만든다.
            ShortestPathVertex pollVertex = surroundVertexes.pollFirst();
            spVertexes.add(pollVertex); // 최단 경로 정점에 추가

            // pollVertex 의 주변 정점들을 확인
            for (Edge edge : pollVertex.vertex.getEdges()) {
                Vertex curVertex = findOppositeVertex(edge, pollVertex.vertex); // 확인할 정점
                ShortestPathVertex curSurVertex = findSPVByVertex(surroundVertexes, curVertex); // 확인할 정점에 대응되는 주변 정점
                ShortestPathVertex curSpVertex = findSPVByVertex(spVertexes, curVertex); // 확인할 정점에 대응되는 최단 경로 정점
                int weight = pollVertex.weightSum + edge.getWeight(); // pollVertex 의 가중치와 간선 가중치의 합

                // 이미 주변 정점이면 가중치를 비교하여 작은 쪽으로 교체
                if (curSurVertex != null && curSurVertex.weightSum > weight) {
                    curSurVertex.weightSum = weight;
                    curSurVertex.path.clear();
                    curSurVertex.path.addAll(pollVertex.path);
                    curSurVertex.path.add(curSurVertex.vertex);
                }
                // 보이지 않는 정점이면 주변 정점에 추가
                else if (curSpVertex == null) {
                    ShortestPathVertex newSpVertex = new ShortestPathVertex(curVertex, weight);
                    newSpVertex.path.addAll(pollVertex.path);
                    newSpVertex.path.add(newSpVertex.vertex);
                    surroundVertexes.add(newSpVertex);
                }
                // 기존 정점이면 기존 값과 pollVertex 의 가중치와 합한 값을 비교하여 작은 쪽으로 교체
                else if (curSpVertex.weightSum > weight) {
                    curSpVertex.weightSum = weight;
                    curSpVertex.path.clear();
                    curSpVertex.path.addAll(pollVertex.path);
                    curSpVertex.path.add(curSpVertex.vertex);
                }
            }
        }

        List<Vertex> shortestPath = null;

        for (ShortestPathVertex spVertex : spVertexes) {
            if (spVertex.vertex.equals(end)) {
                shortestPath = spVertex.path;
                break;
            }
        }

        return shortestPath;
    }

    private static Vertex findOppositeVertex(Edge edge, Vertex vertex) {
        if (edge.getVertex1().equals(vertex) && !edge.getVertex2().equals(vertex)) return edge.getVertex2();
        else if (edge.getVertex2().equals(vertex) && !edge.getVertex1().equals(vertex)) return edge.getVertex1();
        else return null;
    }

    private static ShortestPathVertex findSPVByVertex(Collection<ShortestPathVertex> collection, Vertex vertex) {
        ShortestPathVertex shortestPathVertex = null;
        ShortestPathVertex findShortestPathVertex = new ShortestPathVertex(vertex);
        for (ShortestPathVertex spVertex : collection) {
            if (spVertex.equals(findShortestPathVertex)) {
                shortestPathVertex = findShortestPathVertex;
                break;
            }
        }
        return shortestPathVertex;
    }

}
