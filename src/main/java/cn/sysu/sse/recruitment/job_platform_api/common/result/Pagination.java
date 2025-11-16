package cn.sysu.sse.recruitment.job_platform_api.common.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagination {
	@JsonProperty("total_items")
	private long totalItems;
	@JsonProperty("total_pages")
	private long totalPages;
	@JsonProperty("current_page")
	private long currentPage;
	@JsonProperty("page_size")
	private long pageSize;
}
