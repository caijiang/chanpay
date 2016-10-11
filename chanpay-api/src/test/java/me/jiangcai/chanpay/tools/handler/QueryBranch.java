package me.jiangcai.chanpay.tools.handler;

import com.fasterxml.jackson.databind.JsonNode;
import me.jiangcai.chanpay.model.SubBranch;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CJ
 */
public class QueryBranch extends AbstractHandler<List<SubBranch>> {
    @Override
    protected List<SubBranch> handleData(JsonNode data) {
        JsonNode branch = data.get("branch");
        ArrayList<SubBranch> branchArrayList = new ArrayList<>();
        for (JsonNode one : branch) {
            branchArrayList.add(toSubBranch(one));
        }
        return branchArrayList;
    }

    private SubBranch toSubBranch(JsonNode node) {
        SubBranch subBranch = new SubBranch();
        subBranch.setId(node.get("no").asText());
        subBranch.setName(node.get("name").asText());
        subBranch.setShortName(node.get("sName").asText());
        return subBranch;
    }
}
