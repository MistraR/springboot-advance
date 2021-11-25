package com.advance.mistra.plugin.esannotationversion.repo;

import com.advance.mistra.plugin.esannotationversion.document.MemberDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 描述
 *
 * @author mistra@future.com
 * @date 2021/11/25
 */
public interface MemberIndexRepository extends ElasticsearchRepository<MemberDocument, Long> {
}
