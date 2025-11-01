import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BeneficiarioService, Beneficiario, TransferenciaRequest } from './beneficiario.service';

@Component({
  selector: 'app-beneficiario',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './beneficiario.component.html',
  styleUrls: ['./beneficiario.component.scss']
})
export class BeneficiarioComponent implements OnInit {
  beneficiarios = signal<Beneficiario[]>([]);
  editandoId = signal<number | null>(null);
  
  formulario: Beneficiario = {
    nome: '',
    cpf: '',
    saldo: 0,
    version: 0
  };

  transferencia: TransferenciaRequest = {
    idOrigem: 0,
    idDestino: 0,
    valor: 0
  };

  constructor(private service: BeneficiarioService) {}

  ngOnInit() {
    this.carregarBeneficiarios();
  }

  carregarBeneficiarios() {
    this.service.listar().subscribe(
      data => this.beneficiarios.set(data),
      error => console.error('Erro ao carregar', error)
    );
  }

  salvar() {
    if (this.editandoId()) {
      this.service.atualizar(this.editandoId()!, this.formulario).subscribe(
        () => { this.carregarBeneficiarios(); this.cancelar(); },
        error => console.error('Erro ao atualizar', error)
      );
    } else {
      this.service.criar(this.formulario).subscribe(
        () => { this.carregarBeneficiarios(); this.limparFormulario(); },
        error => console.error('Erro ao criar', error)
      );
    }
  }

  editar(beneficiario: Beneficiario) {
    this.formulario = { ...beneficiario };
    this.editandoId.set(beneficiario.id || null);
  }

  cancelar() {
    this.limparFormulario();
  }

  limparFormulario() {
    this.formulario = { nome: '', cpf: '', saldo: 0, version: 0 };
    this.editandoId.set(null);
  }

  remover(id: number | undefined) {
    if (id && confirm('Confirmar exclusão?')) {
      this.service.deletar(id).subscribe(
        () => this.carregarBeneficiarios(),
        error => console.error('Erro ao deletar', error)
      );
    }
  }

  executarTransferencia() {
    if (this.transferencia.idOrigem && this.transferencia.idDestino && this.transferencia.valor > 0) {
      this.service.transferir(this.transferencia).subscribe(
        () => {
          alert('Transferencia realizada com sucesso');
          this.carregarBeneficiarios();
          this.transferencia = { idOrigem: 0, idDestino: 0, valor: 0};
        },
        error => {
          console.error('Erro na transferencia', error);
          alert('Erro ao realizar transferencia: ' + error.error.message);
        }
      );
    }
  }
}