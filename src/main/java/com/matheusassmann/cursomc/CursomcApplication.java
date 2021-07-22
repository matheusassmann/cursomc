package com.matheusassmann.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.matheusassmann.cursomc.domain.Categoria;
import com.matheusassmann.cursomc.domain.Cidade;
import com.matheusassmann.cursomc.domain.Cliente;
import com.matheusassmann.cursomc.domain.Endereco;
import com.matheusassmann.cursomc.domain.Estado;
import com.matheusassmann.cursomc.domain.ItemPedido;
import com.matheusassmann.cursomc.domain.Pagamento;
import com.matheusassmann.cursomc.domain.PagamentoComBoleto;
import com.matheusassmann.cursomc.domain.PagamentoComCartao;
import com.matheusassmann.cursomc.domain.Pedido;
import com.matheusassmann.cursomc.domain.Produto;
import com.matheusassmann.cursomc.domain.enums.EstadoPagamento;
import com.matheusassmann.cursomc.domain.enums.TipoCliente;
import com.matheusassmann.cursomc.repositories.CategoriaRepository;
import com.matheusassmann.cursomc.repositories.CidadeRepository;
import com.matheusassmann.cursomc.repositories.ClienteRepository;
import com.matheusassmann.cursomc.repositories.EnderecoRepository;
import com.matheusassmann.cursomc.repositories.EstadoRepository;
import com.matheusassmann.cursomc.repositories.ItemPedidoRepository;
import com.matheusassmann.cursomc.repositories.PagamentoRepository;
import com.matheusassmann.cursomc.repositories.PedidoRepository;
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
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

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

		Cliente cliente1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
		
		cliente1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));
		
		clienteRepository.saveAll(Arrays.asList(cliente1));
		
		Endereco endereco1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834", cid3, cliente1);
		Endereco endereco2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cid1, cliente1);
		
		cliente1.getEnderecos().addAll(Arrays.asList(endereco1, endereco2));
		
		enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido pedido1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cliente1, endereco1);
		Pedido pedido2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cliente1, endereco2);
		
		Pagamento pagamento1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, pedido1, 6);
		pedido1.setPagamento(pagamento1);
		
		Pagamento pagamento2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, pedido2, sdf.parse("20/10/2017 00:00"), null);
		pedido2.setPagamento(pagamento2);
		
		cliente1.getPedidos().addAll(Arrays.asList(pedido1, pedido2));
		
		pedidoRepository.saveAll(Arrays.asList(pedido1, pedido2));
		pagamentoRepository.saveAll(Arrays.asList(pagamento1, pagamento2));
		
		ItemPedido ip1 = new ItemPedido(pedido1, prod1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(pedido1, prod3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(pedido2, prod2, 100.00, 1, 800.00);
		
		pedido1.getItens().addAll(Arrays.asList(ip1, ip2));
		pedido2.getItens().addAll(Arrays.asList(ip3));
		
		prod1.getItens().addAll(Arrays.asList(ip1));
		prod2.getItens().addAll(Arrays.asList(ip3));
		prod3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}

}
