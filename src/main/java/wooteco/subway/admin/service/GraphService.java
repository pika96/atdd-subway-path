package wooteco.subway.admin.service;

import org.springframework.stereotype.Service;
import wooteco.subway.admin.domain.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GraphService {
    private Map<PathType, Graph> graphs = new ConcurrentHashMap<>();

    private CustomShortestPathAlgorithm shortestPathAlgorithm;

    public GraphService(CustomShortestPathAlgorithm shortestPathAlgorithm) {
        this.shortestPathAlgorithm = shortestPathAlgorithm;
    }

    public void initialize(Stations stations, Lines lines) {
        Map<Long, Station> stationsCache = stations.convertMap();
        List<LineStation> edges = lines.getEdges();

        graphs.put(PathType.DISTANCE, Graph.of(stationsCache, edges, PathType.DISTANCE, shortestPathAlgorithm));
        graphs.put(PathType.DURATION, Graph.of(stationsCache, edges, PathType.DURATION, shortestPathAlgorithm));
    }

    public ShortestPath getShortestPath(Station source, Station target, PathType pathType) {
        return ShortestPath.of(graphs.get(pathType), source, target);
    }
}
