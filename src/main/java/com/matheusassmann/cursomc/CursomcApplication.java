package com.matheusassmann.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.matheusassmann.cursomc.domain.Categoria;
import com.matheusassmann.cursomc.domain.Cidade;
import com.matheusassmann.cursomc.domain.Estado;
import com.matheusassmann.cursomc.domain.Produto;
import com.matheusassmann.cursomc.repositories.CategoriaRepository;
import com.matheusassmann.cursomc.repositories.CidadeRepository;
import com.matheusassmann.cursomc.repositories.EstadoRepository;
import com.matheusassmann.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");

		Produto prod1 = new Produto(null, "Computador", 2000.00);
		Produto prod2 = new Produto(null, "Impressora", 800.00);
		Produto prod3 = new Produto(null, "Mouse", 80.00);

		cat1.getProdutos().addAll(Arrays.asList(prod1, prod2, prod3));
		cat2.getProdutos().addAll(Arrays.asList(prod2));

		prod1.getCategorias().addAll(Arrays.asList(cat1));
		prod2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		prod3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(prod1, prod2, prod3));

		Estado uf1 = new Estado(null, "São Paulo");
		Estado uf2 = new Estado(null, "Minas Gerais");

		Cidade cid1 = new Cidade(null, "São Paulo", uf1);
		Cidade cid2 = new Cidade(null, "Campinas", uf1);
		Cidade cid3 = new Cidade(null, "Uberlândia", uf2);

		uf1.getCidades().addAll(Arrays.asList(cid1, cid2));
		uf2.getCidades().addAll(Arrays.asList(cid3));

		estadoRepository.saveAll(Arrays.asList(uf1, uf2));
		cidadeRepository.saveAll(Arrays.asList(cid1, cid2, cid3));

	}

}
