package io.github.lubosgarancovsky.springutilities.jooq.handler;


import cz.jirutka.rsql.parser.RSQLParserException;
import cz.jirutka.rsql.parser.ast.Node;
import io.github.lubosgarancovsky.springutilities.error.BusinessException;
import io.github.lubosgarancovsky.springutilities.jooq.builder.JooqRsqlSelectBuilder;
import io.github.lubosgarancovsky.springutilities.jooq.builder.JooqRsqlWithListingMetadataBuilder;
import io.github.lubosgarancovsky.springutilities.jooq.metadata.JooqRsqlMetadataConfigProvider;
import io.github.lubosgarancovsky.springutilities.jooq.metadata.JooqRsqlWithMetadataParser;
import io.github.lubosgarancovsky.springutilities.rsql.RsqlErrorCode;
import io.github.lubosgarancovsky.springutilities.rsql.RsqlNodeParser;
import org.jooq.SelectQuery;

import java.util.Optional;

public class JooqRsqlSelectQueryHandler implements JooqOneParamSelectQueryHandler<SelectQuery> {
    
    private final Optional<Node> rsqlQuery;

    private final JooqRsqlMetadataConfigProvider rsqlParserConfiguration;

    public JooqRsqlSelectQueryHandler(Optional<Node> rsqlQuery, JooqRsqlMetadataConfigProvider rsqlParserConfiguration) {
        this.rsqlQuery = rsqlQuery;
        this.rsqlParserConfiguration = rsqlParserConfiguration;
    }

    @Override
    public SelectQuery handle(SelectQuery query) throws BusinessException {
        if(this.rsqlQuery.isPresent()) {
            JooqRsqlWithListingMetadataBuilder<SelectQuery> builder = new JooqRsqlSelectBuilder(query);
        
            RsqlNodeParser<SelectQuery> rsqlParser = new JooqRsqlWithMetadataParser(builder, this.rsqlParserConfiguration);

            try {
                rsqlParser.parse(this.rsqlQuery.get());
            } catch(RSQLParserException e) {
                throw RsqlErrorCode.PARSE_RSQL_EXCEPTION.createError(new String[0]).convertToException();
            }
        }
        
        return query;
    }


}
