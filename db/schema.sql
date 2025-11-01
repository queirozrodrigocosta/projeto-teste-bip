CREATE TABLE beneficiario (
  id LONG PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(100) NOT NULL,
  cpf VARCHAR(14) UNIQUE NOT NULL,
  saldo DECIMAL(15,2) NOT NULL DEFAULT 0,
  version INT NOT NULL DEFAULT 0,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE transferencia (
  id LONG PRIMARY KEY AUTO_INCREMENT,
  beneficiario_origem_id LONG NOT NULL,
  beneficiario_destino_id LONG NOT NULL,
  valor DECIMAL(15,2) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (beneficiario_origem_id) REFERENCES beneficiario(id),
  FOREIGN KEY (beneficiario_destino_id) REFERENCES beneficiario(id)
);

CREATE INDEX idx_beneficiario_cpf ON beneficiario(cpf);
CREATE INDEX idx_transferencia_origem ON transferencia(beneficiario_origem_id);
CREATE INDEX idx_transferencia_destino ON transferencia(beneficiario_destino_id);
