function operator(op) {
    if (op == ">=") return "\u2265";
    else if (op == "<=") return "\u2264";
    else return op;
}

function conditionsToString(conditions) {
    var result = "";
    for (condition of conditions) {
        result += "\n" + condition.name + " " + operator(condition.operator) + " " + condition.description;
    }
    return result.substr(1);
}

function decisionsToString(decisions) {
    var decision = decisions[0][0];
    return decision.name + " " + operator(decision.operator) + " " + decision.description;
}

Vue.component('rule-row', {
    props: ['rule'],
    template: `<tr>
        <td>{{ rule.id }}</td>
        <td><span style="white-space: pre;">{{ conditionsToString(rule.conditions) }}</span></td>
        <td>{{ decisionsToString(rule.decisions) }}</td>
        <td>{{ rule.characteristics.Support }}</td>
    </tr>`,
    methods: {
        conditionsToString, decisionsToString
    }
});

var app = new Vue({
    el: '#app',
    data: {
        activetab: 1,
        loadMsg: "",
        rules: [],
        attributes: [],
        characteristics: []
    },
    methods: {
        browseAttributes, browseRules, setAttributes, setRules, submit, loadDemo
    }
});


 
//Object.freeze(obj);




