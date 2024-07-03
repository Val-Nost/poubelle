const data = {
    "Rue Croix-Baragnon": [
        "La Défense", "Esplanade de la Défense", "Pont de Neuilly", "Les Sablons", "Porte Maillot", "Argentine",
        "Charles de Gaulle-Étoile", "George V", "Franklin D. Roosevelt", "Champs-Élysées-Clemenceau", "Concorde",
        "Tuileries", "Palais Royal-Musée du Louvre", "Louvre-Rivoli", "Châtelet", "Hôtel de Ville", "Saint-Paul",
        "Bastille", "Gare de Lyon", "Reuilly-Diderot", "Nation", "Porte de Vincennes", "Saint-Mandé", "Bérault",
        "Château de Vincennes"
    ],
    "Rue des Arts": [
        "Porte Dauphine", "Victor Hugo", "Charles de Gaulle-Étoile", "Ternes", "Courcelles", "Monceau", "Villiers",
        "Rome", "Place de Clichy", "Blanche", "Pigalle", "Anvers", "Barbès-Rochechouart", "La Chapelle", "Stalingrad",
        "Jaurès", "Colonel Fabien", "Belleville", "Couronnes", "Ménilmontant", "Père Lachaise", "Philippe Auguste",
        "Alexandre Dumas", "Avron", "Nation"
    ],
    "Rue Pargaminières": [
        "Pont de Levallois-Bécon", "Anatole France", "Louise Michel", "Porte de Champerret", "Pereire", "Wagram",
        "Malesherbes", "Villiers", "Europe", "Saint-Lazare", "Havre-Caumartin", "Opéra", "Quatre-Septembre", "Bourse",
        "Sentier", "Réaumur-Sébastopol", "Arts et Métiers", "Temple", "République", "Parmentier", "Rue Saint-Maur",
        "Père Lachaise", "Gambetta", "Porte de Bagnolet", "Gallieni"
    ],
    "Rue Saint-Rome": [
        "Gambetta", "Pelleport", "Saint-Fargeau", "Porte des Lilas"
    ],
    "Rue Saint-Antoine du T": [
        "Porte de Clignancourt", "Simplon", "Marcadet-Poissonniers", "Château Rouge", "Barbès-Rochechouart",
        "Gare du Nord", "Gare de l'Est", "Château d'Eau", "Strasbourg-Saint-Denis", "Réaumur-Sébastopol", "Étienne Marcel",
        "Les Halles", "Châtelet", "Cité", "Saint-Michel-Notre-Dame", "Odéon", "Saint-Germain-des-Prés", "Saint-Sulpice",
        "Saint-Placide", "Montparnasse-Bienvenüe", "Vavin", "Raspail", "Denfert-Rochereau", "Mouton-Duvernet", "Alésia",
        "Porte d'Orléans"
    ],
    "Rue de la Fonderie": [
        "Bobigny-Pablo Picasso", "Bobigny-Pantin-Raymond Queneau", "Église de Pantin", "Hoche", "Porte de Pantin",
        "Ourcq", "Laumière", "Jaurès", "Stalingrad", "Gare du Nord", "Gare de l'Est", "Jacques Bonsergent", "République",
        "Oberkampf", "Richard-Lenoir", "Bréguet-Sabin", "Bastille", "Quai de la Rapée", "Gare d'Austerlitz",
        "Saint-Marcel", "Campo-Formio", "Place d'Italie"
    ],
    "Rue Peyrolières": [
        "Charles de Gaulle-Étoile", "Kléber", "Boissière", "Trocadéro", "Passy", "Champ de Mars-Tour Eiffel", "Dupleix",
        "La Motte-Picquet-Grenelle", "Cambronne", "Sèvres-Lecourbe", "Pasteur", "Montparnasse-Bienvenüe", "Edgar Quinet",
        "Raspail", "Denfert-Rochereau", "Saint-Jacques", "Glacière", "Corvisart", "Place d'Italie", "Nationale",
        "Chevaleret", "Quai de la Gare", "Bercy", "Dugommier", "Daumesnil", "Bel-Air", "Picpus", "Nation"
    ],
    "Rue Genty-Magre": [
        "La Courneuve-8 Mai 1945", "Fort d'Aubervilliers", "Aubervilliers-Pantin-Quatre Chemins", "Porte de la Villette",
        "Corentin Cariou", "Crimée", "Riquet", "Stalingrad", "Louis Blanc", "Château-Landon", "Gare de l'Est",
        "Poissonnière", "Cadet", "Le Peletier", "Chaussée d'Antin-La Fayette", "Opéra", "Pyramides",
        "Palais Royal-Musée du Louvre", "Pont Neuf", "Châtelet", "Pont Marie", "Sully-Morland", "Jussieu", "Place Monge",
        "Censier-Daubenton", "Les Gobelins", "Place d'Italie", "Tolbiac", "Maison Blanche", "Porte d'Italie",
        "Porte de Choisy", "Porte d'Ivry", "Pierre et Marie Curie", "Mairie d'Ivry"
    ],
    "Rue d'Alsace-Lorraine": [
        "Louis Blanc", "Jaurès", "Bolivar", "Buttes Chaumont", "Botzaris", "Place des Fêtes", "Pré Saint-Gervais"
    ],
    "Rue Peyras": [
        "Balard", "Lourmel", "Boucicaut", "Félix Faure", "Commerce", "La Motte-Picquet-Grenelle", "École Militaire",
        "La Tour-Maubourg", "Invalides", "Concorde", "Madeleine", "Opéra", "Richelieu-Drouot", "Grands Boulevards",
        "Bonne Nouvelle", "Strasbourg-Saint-Denis", "République", "Filles du Calvaire", "Saint-Sébastien-Froissart",
        "Chemin Vert", "Bastille", "Ledru-Rollin", "Faidherbe-Chaligny", "Reuilly-Diderot", "Montgallet", "Daumesnil",
        "Michel Bizot", "Porte Dorée", "Porte de Charenton", "Liberté", "Charenton-Écoles",
        "École Vétérinaire de Maisons-Alfort", "Maisons-Alfort-Stade", "Maisons-Alfort-Les Juilliottes", "Créteil-L'Échat",
        "Créteil-Université", "Créteil-Préfecture"
    ],
    "Rue du Taur": [
        "Pont de Sèvres", "Billancourt", "Marcel Sembat", "Porte de Saint-Cloud", "Exelmans", "Michel-Ange-Molitor",
        "Michel-Ange-Auteuil", "Jasmin", "Ranelagh", "La Muette", "Rue de la Pompe", "Trocadéro", "Iéna",
        "Alma-Marceau", "Franklin D. Roosevelt", "Saint-Philippe du Roule", "Miromesnil", "Saint-Augustin",
        "Havre-Caumartin", "Chaussée d'Antin-La Fayette", "Richelieu-Drouot", "Grands Boulevards", "Bonne Nouvelle",
        "Strasbourg-Saint-Denis", "République", "Oberkampf", "Saint-Ambroise", "Voltaire", "Charonne", "Rue des Boulets",
        "Nation", "Buzenval", "Maraîchers", "Porte de Montreuil", "Robespierre", "Croix de Chavaux", "Mairie de Montreuil"
    ],
    "Allée Jean Jaurès": [
        "Boulogne-Pont de Saint-Cloud", "Boulogne-Jean Jaurès", "Porte d'Auteuil", "Michel-Ange-Auteuil", "Église d'Auteuil",
        "Javel-André Citroën", "Charles Michels", "Avenue Émile Zola", "La Motte-Picquet-Grenelle", "Ségur", "Duroc",
        "Vaneau", "Sèvres-Babylone", "Mabillon", "Odéon", "Cluny-La Sorbonne", "Maubert-Mutualité", "Cardinal Lemoine",
        "Jussieu", "Gare d'Austerlitz"
    ],
    "Rue du May": [
        "Châtelet", "Hôtel de Ville", "Rambuteau", "Arts et Métiers", "République", "Goncourt", "Belleville", "Pyrénées",
        "Jourdain", "Place des Fêtes", "Télégraphe", "Porte des Lilas", "Mairie des Lilas"
    ],
    "Rue des Filatiers": [
        "Porte de la Chapelle", "Marx Dormoy", "Marcadet-Poissonniers", "Jules Joffrin", "Lamarck-Caulaincourt", "Abbesses",
        "Pigalle", "Saint-Georges", "Notre-Dame-de-Lorette", "Trinité-d'Estienne d'Orves", "Saint-Lazare", "Madeleine",
        "Concorde", "Assemblée nationale", "Solférino", "Rue du Bac", "Sèvres-Babylone", "Rennes", "Notre-Dame-des-Champs",
        "Montparnasse-Bienvenüe", "Falguière", "Pasteur", "Volontaires", "Vaugirard", "Convention", "Porte de Versailles",
        "Corentin Celton", "Mairie d'Issy"
    ],
    "Rue Mage": [
        "Saint-Denis-Université", "Basilique de Saint-Denis", "Saint-Denis-Porte de Paris", "Carrefour Pleyel",
        "Mairie de Saint-Ouen", "Garibaldi", "Porte de Saint-Ouen", "Guy Môquet", "La Fourche", "Place de Clichy",
        "Liège", "Saint-Lazare", "Miromesnil", "Champs-Élysées-Clemenceau", "Invalides", "Varenne", "Saint-François-Xavier",
        "Duroc", "Montparnasse-Bienvenüe", "Gaîté", "Pernety", "Plaisance", "Porte de Vanves", "Malakoff-Plateau de Vanves",
        "Malakoff-Rue Etienne Dolet", "Châtillon-Montrouge"
    ],
    "Rue d'Espinasse": [
        "Saint-Lazare", "Madeleine", "Pyramides", "Châtelet", "Gare de Lyon", "Bercy", "Cour Saint-Émilion",
        "Bibliothèque François Mitterrand", "Olympiades"
    ],
    "Rue des Gestes": [
        "Gare Saint-Denis", "Théâtre Gérard Philipe", "Marché de Saint-Denis", "Basilique de Saint-Denis", "Cimetière de Saint-Denis",
        "Hôpital Delafontaine", "Cosmonautes", "La Courneuve-Six Routes", "Hôtel de Ville de La Courneuve", "Stade Géo André",
        "Danton", "La Courneuve-8 Mai 1945", "Maurice Lachâtre", "Drancy-Avenir", "Hôpital Avicenne", "Gaston Roulaud",
        "Escadrille Normandie-Niémen", "La Ferme", "Libération", "Hôtel de Ville de Bobigny", "Bobigny-Pablo Picasso",
        "Jean Rostand", "Auguste Delaune", "Pont de Bondy", "Petit Noisy", "Noisy-le-Sec"
    ],
    "Quai de la Daurade": [
        "La Défense", "Puteaux", "Belvédère", "Suresnes-Longchamp", "Les Coteaux", "Les Milons", "Parc de Saint-Cloud",
        "Musée de Sèvres", "Brimborion", "Meudon-sur-Seine", "Les Moulineaux", "Jacques-Henri Lartigue", "Issy-Val de Seine",
        "Balard", "Porte de Versailles"
    ],
    "Rue Bédelières": [
        "Pont du Garigliano", "Balard", "Desnouettes", "Porte de Versailles", "Georges Brassens", "Brancion",
        "Porte de Vanves", "Didot", "Jean Moulin", "Porte d'Orléans", "Montsouris", "Cité universitaire", "Stade Charléty",
        "Poterne des Peupliers", "Porte d'Italie", "Porte de Choisy", "Porte d'Ivry", "Bibliothèque François Mitterrand",
        "Porte de Charenton", "Porte Dorée", "Montempoivre", "Porte de Vincennes"
    ],
    "Rue Merlane": [
        "La Défense", "Charles de Gaulle-Étoile", "Auber", "Châtelet-Les Halles", "Gare de Lyon", "Nation"
    ],
    "Rue Vélane": [
        "Gare du Nord", "Châtelet-Les Halles", "Saint-Michel-Notre-Dame", "Luxembourg", "Port-Royal", "Denfert-Rochereau",
        "Cité Universitaire"
    ],
    "Rue Etroite": [
        "Porte de Clichy", "Pereire", "Porte Maillot", "Avenue Foch", "Avenue Henri Martin", "La Muette", "Avenue du Président Kennedy",
        "Champ de Mars-Tour Eiffel", "Pont de l'Alma", "Invalides", "Musée d'Orsay", "Saint-Michel-Notre-Dame",
        "Gare d'Austerlitz", "Bibliothèque François Mitterrand"
    ],
    "Rue des Tourneurs": [
        "Gare du Nord", "Châtelet-Les Halles", "Gare de Lyon"
    ],
    "Rue de la Trinité": [
        "Saint-Lazare", "Gare du Nord"
    ]
};


let nodes = {};
let links = [];
let rues = Object.keys(data);

let color = d3.scaleOrdinal(d3.schemeSet3);

// Create a set to track intersections
let intersections = new Set();

Object.keys(data).forEach(rue => {
    const arrets = data[rue];
    arrets.forEach((arret, index) => {
        if (!nodes[arret]) {
            nodes[arret] = { id: arret, group: rue, count: 1 };
        } else {
            nodes[arret].count++;
            intersections.add(arret);
        }
        if (index > 0) {
            links.push({ source: arrets[index - 1], target: arret, group: rue, name: rue });
        }
    });
});

nodes = Object.values(nodes);

const svg = d3.select("svg"),
    width = +svg.attr("width"),
    height = +svg.attr("height");

const zoom = d3.zoom()
    .scaleExtent([0.1, 10])
    .on("zoom", zoomed);

const g = svg.append("g");

svg.call(zoom);

const simulation = d3.forceSimulation(nodes)
    .force("link", d3.forceLink(links).id(d => d.id).distance(50).strength(1))
    .force("charge", d3.forceManyBody().strength(-50))
    .force("center", d3.forceCenter(width / 2, height / 2))
    .force("collide", d3.forceCollide().radius(d => d.count > 1 ? 20 : 10).iterations(2))
    .force("y", d3.forceY().strength(0.1))
    .force("x", d3.forceX().strength(0.1));

// Create a line generator for straight lines and arcs
const lineGenerator = d3.line()
    .x(d => d.x)
    .y(d => d.y)
    .curve(d3.curveBasis);  // Use curveBasis for smooth curved paths

const link = g.append("g")
    .attr("class", "links")
    .selectAll("path")
    .data(links)
    .enter().append("path")
    .attr("class", "link")
    .attr("stroke", d => color(d.group))
    .attr("stroke-width", 6)
    .attr("fill", "none");

const uniqueRueLabels = new Map();
links.forEach(link => {
    if (!uniqueRueLabels.has(link.name)) {
        uniqueRueLabels.set(link.name, link);
    }
});

const linkText = g.append("g")
    .attr("class", "link-text")
    .selectAll("text")
    .data(Array.from(uniqueRueLabels.values()))
    .enter().append("text")
    .attr("class", "link-label")
    .attr("dy", -5)
    .style("font-size", "10px")
    .style("pointer-events", "none")
    .append("textPath")
    .attr("xlink:href", (d, i) => `#linkPath${i}`)
    .attr("startOffset", "50%")
    .style("text-anchor", "middle")
    .text(d => d.name);

const node = g.append("g")
    .attr("class", "nodes")
    .selectAll("g")
    .data(nodes)
    .enter().append("g")
    .attr("class", "node");

node.append("circle")
    .attr("r", d => d.count > 1 ? 10 : 5)
    .attr("fill", d => color(d.group))
    .attr("stroke-width", 2);

node.append("text")
    .attr("dx", 8)
    .attr("dy", ".35em")
    .style("font-size", "8px")
    .text(d => d.id);

simulation
    .nodes(nodes)
    .on("tick", ticked);

simulation.force("link")
    .links(links);

function ticked() {
    link
        .attr("d", d => {
            const points = [
                { x: d.source.x, y: d.source.y },
                { x: (d.source.x + d.target.x) / 2, y: (d.source.y + d.target.y) / 2 },
                { x: d.target.x, y: d.target.y }
            ];
            return lineGenerator(points);
        });

    linkText.select("textPath")
        .attr("xlink:href", (d, i) => `#linkPath${i}`)
        .text(d => d.name);

    node
        .attr("transform", d => `translate(${d.x},${d.y})`);
}

function zoomed(event) {
    g.attr("transform", event.transform);
}