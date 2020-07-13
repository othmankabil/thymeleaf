package org.sid.cinema.web;


import org.sid.cinema.dao.*;
import org.sid.cinema.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@Controller

public class CinemaController {

    @Autowired
    private CinemaRepository cinemaRepository;


    @Autowired
    public FilmRepository filmRepository;
    
    @Autowired
    public VilleRepository villeRepository;

    @Autowired
    public CategorieRepository categorieRepository;

    @Autowired
    public TicketRepository ticketRepository;

    @Autowired
    public SalleRepository salleRepository;


    @Autowired
    public PlaceRepository placeRepository;

    @Autowired
    public ProjectionRepository projectionRepository;

    @Autowired
    public SeanceRepository seanceRepository;

    @GetMapping(path = "index")
    public String index(){
        return "index";
    }

    @GetMapping(path = "/listeCinemas")
    public String ListeCinemas(Model model,
                               @RequestParam(name = "page",defaultValue = "0")int page,
                               @RequestParam(name = "size",defaultValue = "10")int size,
                               @RequestParam(name = "keyword",defaultValue = "")String kw){
        Page<Cinema> cinemas=cinemaRepository.findByNameContains(kw, PageRequest.of(page, size));
        model.addAttribute("listedesCinemas",cinemas.getContent());
        model.addAttribute("pages",new int[cinemas.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",kw);
        model.addAttribute("size",size);
        return "listeCinemas";
    }

    @GetMapping(path = "/deleteCinema")
    public String deleteCinema(Long id){
        cinemaRepository.deleteById(id);
        return "redirect:/listeCinemas";
    }

    @GetMapping(path = "/addTest")
    public String addTest(Model model){
        List<Ville> villes = villeRepository.findAll();
        model.addAttribute("listedesvilles",villes);
        model.addAttribute("cinema",new Cinema());
        model.addAttribute("mode","new");
        return "addTest";
    }

    @PostMapping("/saveCinema")
    public String saveCinema(Model model, @Valid  Cinema cinema, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "addTest";
        cinemaRepository.save(cinema);
        model.addAttribute("cinema", cinema);
        return "confirmationCinema";
    }

    @GetMapping(path = "/editCinema")
    public String editCinema(Model model,Long id){
        Cinema c= cinemaRepository.findById(id).get();
        List<Ville> villes = villeRepository.findAll();
        model.addAttribute("listedesvilles",villes);
        model.addAttribute("cinema",c);
        model.addAttribute("mode","edit");
        return "addTest";
    }

    @GetMapping(path = "/listeVilles")
    public String ListeVilles(Model model,
                              @RequestParam(name = "page",defaultValue = "0")int page,
                              @RequestParam(name = "size",defaultValue = "10")int size,
                              @RequestParam(name = "keyword",defaultValue = "")String kw){
        Page<Ville> villes = villeRepository.findByNameContains(kw, PageRequest.of(page, size));
        model.addAttribute("listedesvilles",villes.getContent());
        model.addAttribute("pages",new int[villes.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",kw);
        model.addAttribute("size",size);
        return "listeVilles";
    }

    @GetMapping(path = "/deleteVilles")
    public String deleteVilles(Long id){
        villeRepository.deleteById(id);
        return "redirect:/listeVilles";
    }

    @GetMapping(path = "/addVille")
    public String addVille(Model model){
        model.addAttribute("ville",new Ville());
        return "/addVille";
    }

    @PostMapping("/saveVille")
    public String saveVille(Model model, @Valid  Ville ville, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "addVille";
        villeRepository.save(ville);
        model.addAttribute("ville",ville);
        return "ConfirmationVille";
    }

    @GetMapping(path = "/editVille")
    public String editVille(Model model,Long id){
        Ville c= villeRepository.findById(id).get();
        model.addAttribute("ville",c);
        model.addAttribute("mode","edit");
        return "addVille";
    }


    @GetMapping(path = "/listeCategories")
    public String ListeCategories(Model model,
                                  @RequestParam(name = "page",defaultValue = "0")int page,
                                  @RequestParam(name = "size",defaultValue = "10")int size,
                                  @RequestParam(name = "keyword",defaultValue = "")String kw){
        Page<Categorie> categories = categorieRepository.findByNameContains(kw, PageRequest.of(page, size));
        model.addAttribute("listedesCategories",categories.getContent());
        model.addAttribute("pages",new int[categories.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",kw);
        model.addAttribute("size",size);
        return "listeCategories";
    }

    @GetMapping(path = "/deleteCategories")
    public String deleteCategories(Long id){
        categorieRepository.deleteById(id);
        return "redirect:/listeCategories";
    }

    @GetMapping(path = "/addCategorie")
    public String addCategorie(Model model){
        model.addAttribute("categorie",new Categorie());
        return "/addCategorie";
    }

    @PostMapping("/saveCategorie")
    public String saveCategorie(Model model, @Valid  Categorie categorie, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "addCategorie";
        categorieRepository.save(categorie);
        model.addAttribute("categorie",categorie);
        return "ConfirmationCategorie";
    }

    @GetMapping(path = "/editCategorie")
    public String editCategorie(Model model,Long id){
        Categorie c= categorieRepository.findById(id).get();
        model.addAttribute("categorie",c);
        model.addAttribute("mode","edit");
        return "addCategorie";
    }

    @GetMapping(path = "/listeTickets")
    public String ListeTickets(Model model,
                               @RequestParam(name = "page",defaultValue = "0")int page,
                               @RequestParam(name = "size",defaultValue = "25")int size){
        Page<Ticket> tickets = ticketRepository.findAll( PageRequest.of(page, size));
        model.addAttribute("listedesTickets",tickets.getContent());
        model.addAttribute("pages",new int[tickets.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("size",size);
        return "listeTickets";
    }

    @GetMapping(path = "/deleteTickets")
    public String deleteTickets(Long id){
        ticketRepository.deleteById(id);
        return "redirect:/listeTickets";
    }

    @GetMapping(path = "/addTicket")
    public String addTicket(Model model){
        List<Place> places = placeRepository.findAll();
        List<Projection> projections = projectionRepository.findAll();
        model.addAttribute("listedesPlaces",places);
        model.addAttribute("listedesProjections",projections);
        model.addAttribute("ticket",new Ticket());
        return "/addTicket";
    }

    @PostMapping("/saveTicket")
    public String saveTicket(Model model, @Valid  Ticket ticket, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "addTicket";
        ticketRepository.save(ticket);
        model.addAttribute("ticket",ticket);
        return "ConfirmationTicket";
    }

    @GetMapping(path = "/editTicket")
    public String editTicket(Model model,Long id){
        Ticket c= ticketRepository.findById(id).get();
        List<Place> places = placeRepository.findAll();
        List<Projection> projections = projectionRepository.findAll();
        model.addAttribute("listedesPlaces",places);
        model.addAttribute("listedesProjections",projections);
        model.addAttribute("ticket",c);
        model.addAttribute("mode","edit");
        return "addTicket";
    }


    @GetMapping(path = "/listeSalles")
    public String ListeSalles(Model model,
                              @RequestParam(name = "page",defaultValue = "0")int page,
                              @RequestParam(name = "size",defaultValue = "10")int size,
                              @RequestParam(name = "keyword",defaultValue = "")String kw){
        Page<Salle> salles = salleRepository.findByNameContains(kw, PageRequest.of(page, size));
        model.addAttribute("listedesSalles",salles.getContent());
        model.addAttribute("pages",new int[salles.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",kw);
        model.addAttribute("size",size);
        return "listeSalles";
    }

    @GetMapping(path = "/deleteTSalles")
    public String deleteSalles(Long id){
        salleRepository.deleteById(id);
        return "redirect:/listeSalles";
    }

    @GetMapping(path = "/addSalle")
    public String addSalle(Model model){
        List<Cinema> cinemas = cinemaRepository.findAll();
        model.addAttribute("listedescinemas", cinemas);
        model.addAttribute("salle",new Salle());
        return "/addSalle";
    }

    @PostMapping("/saveSalle")
    public String savePlace(Model model, @Valid  Salle salle, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "addSalle";
        salleRepository.save(salle);
        model.addAttribute("salle",salle);
        return "ConfirmationSalle";
    }

    @GetMapping(path = "/editSalle")
    public String editSalle(Model model,Long id){
        Salle c= salleRepository.findById(id).get();
        List<Cinema> cinemas = cinemaRepository.findAll();
        model.addAttribute("listedescinemas", cinemas);
        model.addAttribute("salle",c);
        model.addAttribute("mode","edit");
        return "addSalle";
    }

    @GetMapping(path = "/listePlaces")
    public String ListePlaces(Model model,
                              @RequestParam(name = "page",defaultValue = "0")int page,
                              @RequestParam(name = "size",defaultValue = "10")int size){
        Page<Place> places = placeRepository.findAll(PageRequest.of(page, size));
        model.addAttribute("listedesPlaces",places.getContent());
        model.addAttribute("pages",new int[places.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("size",size);
        return "listePlaces";
    }

    @GetMapping(path = "/deleteTPlaces")
    public String deletePlaces(Long id){
        placeRepository.deleteById(id);
        return "redirect:/listePlaces";
    }

    @GetMapping(path = "/addPlace")
    public String addPlace(Model model){
        List<Salle> salles = salleRepository.findAll();
        model.addAttribute("listedesSalles",salles);
        model.addAttribute("place",new Place());
        return "/addPlace";
    }

    @PostMapping("/savePlace")
    public String savePlace(Model model, @Valid  Place place, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "addPlace";
        placeRepository.save(place);
        model.addAttribute("place",place);
        return "ConfirmationPlace";
    }

    @GetMapping(path = "/editPlace")
    public String editPlace(Model model,Long id){
        Place c= placeRepository.findById(id).get();
        List<Salle> salles = salleRepository.findAll();
        model.addAttribute("listedesSalles",salles);
        model.addAttribute("place",c);
        model.addAttribute("mode","edit");
        return "addPlace";
    }

    @GetMapping(path = "/listeSeances")
    public String ListeSeances(Model model,
                               @RequestParam(name = "page",defaultValue = "0")int page,
                               @RequestParam(name = "size",defaultValue = "10")int size){
        Page<Seance> seances = seanceRepository.findAll(PageRequest.of(page, size));
        model.addAttribute("listedesSeances",seances.getContent());
        model.addAttribute("pages",new int[seances.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("size",size);
        return "listeSeances";
    }

    @GetMapping(path = "/deleteTSeances")
    public String deleteSeances(Long id){
        seanceRepository.deleteById(id);
        return "redirect:/listeSeances";
    }

    @GetMapping(path = "/addSeance")
    public String addSeance(Model model){
        model.addAttribute("seance",new Seance());
        return "/addSeance";
    }

    @PostMapping("/saveSeance")
    public String saveSeance(Model model, @Valid  Seance seance, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "addSeance";
        seanceRepository.save(seance);
        model.addAttribute("seance",seance);
        return "ConfirmationSeance";
    }

    @GetMapping(path = "/editSeance")
    public String editSeance(Model model,Long id){
        Seance c= seanceRepository.findById(id).get();
        model.addAttribute("seance",c);
        model.addAttribute("mode","edit");
        return "addSeance";
    }

    @GetMapping(path = "/listeFilms")
    public String ListeFilms(Model model,
                             @RequestParam(name = "page",defaultValue = "0")int page,
                             @RequestParam(name = "size",defaultValue = "10")int size,
                             @RequestParam(name = "keyword",defaultValue = "")String kw){
        Page<Film> films = filmRepository.findByTitreContains(kw, PageRequest.of(page, size));
        model.addAttribute("listedesFilms",films.getContent());
        model.addAttribute("pages",new int[films.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",kw);
        model.addAttribute("size",size);
        return "listeFilms";
    }


    @GetMapping(path = "/deleteFilms")
    public String deleteFilms(Long id){
        filmRepository.deleteById(id);
        return "redirect:/listeFilms";
    }

    
        @GetMapping(path = "/addFilm")
        public String addFilm(Model model){
            List<Categorie> categories = categorieRepository.findAll();
            model.addAttribute("categories",categories);
            model.addAttribute("film",new Film());
            return "addFilm";
        }
    
        @PostMapping("/saveFilm")
        public String saveFilm(Model model, @Valid  Film film, BindingResult bindingResult){
            filmRepository.save(film);
            model.addAttribute("film",film);
            return "ConfirmationFilm";
        }

    @GetMapping(path = "/editFilm")
    public String editFilm(Model model,Long id){
        Film c= filmRepository.findById(id).get();
        model.addAttribute("film",c);
        List<Categorie> categories = categorieRepository.findAll();

        model.addAttribute("categories",categories);
        model.addAttribute("mode","edit");
        return "addFilm";
    }

    @GetMapping(path = "/listeProjections")
    public String ListeProjections(Model model,
                                   @RequestParam(name = "page",defaultValue = "0")int page,
                                   @RequestParam(name = "size",defaultValue = "10")int size){
        Page<Projection> projections = projectionRepository.findAll(PageRequest.of(page, size));
        model.addAttribute("listedesProjections",projections.getContent());
        model.addAttribute("pages",new int[projections.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("size",size);
        return "listeProjections";
    }

    @GetMapping(path = "/deleteProjections")
    public String deleteProjections(Long id){
        projectionRepository.deleteById(id);
        return "redirect:/listeProjections";
    }

    @GetMapping(path = "/addProjection")
    public String addProjection(Model model){
        List<Film> films = filmRepository.findAll();
        List<Seance> seances = seanceRepository.findAll();
        model.addAttribute("listedesfilms",films);
        model.addAttribute("listedesseances", seances);
        model.addAttribute("projection",new Projection());
        return "/addProjection";
    }

    @PostMapping("/savePrpjection")
    public String saveProjection(Model model, @Valid  Projection projection, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "addProjection";
        projectionRepository.save(projection);
        model.addAttribute("projection",projection);
        return "ConfirmationProjection";
    }

    @GetMapping(path = "/editProjection")
    public String editPrpjection(Model model,Long id){
        Projection c= projectionRepository.findById(id).get();
        List<Film> films = filmRepository.findAll();
        List<Seance> seances = seanceRepository.findAll();
        model.addAttribute("listedesfilms",films);
        model.addAttribute("listedesseances", seances);
        model.addAttribute("projection",c);
        model.addAttribute("mode","edit");
        return "addProjection";
    }

}
