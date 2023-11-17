const data = [
    {
        name: "vase",
        translation: "ο αμφορέας",
        source:
            "https://d2pur3iezf4d1j.cloudfront.net/images/52ed92173fcf38cbdeb76c34102fb5cf"
    },
    {
        name: "carrot",
        translation: "το καρότο",
        source:
            "https://d2pur3iezf4d1j.cloudfront.net/images/c850ea340852d0acd87835dc513a053e"
    },
    {
        name: "carrot",
        translation: "το νερό",
        source:
            "https://d2pur3iezf4d1j.cloudfront.net/images/7afea32bcf0e8c6f9d446ad4aad416be"
    }
];

// DOM ref handles
const content = document.querySelector(".middle");

// Programmatically create cards from template
data.forEach((d) => {
    const card = `
				<div class="card">
					<img src=${d.source}>
				</div>
				`;
    content.innerHTML += card;
});
