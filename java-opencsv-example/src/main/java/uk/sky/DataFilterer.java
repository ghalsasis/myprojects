package uk.sky;

import java.util.List;
import java.util.Map;

public interface DataFilterer {

	Map<String, List<LogRecord>> filterByCountry(List<LogRecord> records);

	List<LogRecord> filterByResponseTimeAboveAverage(List<LogRecord> records);

	Map<String, List<LogRecord>> filterByCountryWithResponseTimeAboveLimit(List<LogRecord> records, double limit);

}
