package cn.sysu.sse.recruitment.job_platform_api.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagination {
	private long totalItems;
	private long totalPages;
	private long currentPage;
	private long pageSize;
}
