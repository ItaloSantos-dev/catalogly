import { Component, computed, inject, signal } from '@angular/core';
import { HelperService } from '../../../../../service/helper/helper-service';
import { SupplierItemResponseDTO } from '../../../../../../types/supplier-item/supplier-item-response';
import { SupplierResponseDTO } from '../../../../../../types/supplier/supplier-response';
import { ContactSupplierType } from '../../../../../../types/enums/contact-supplier-type';
import { SupplierService } from '../../../../../service/supplier/supplier-service';

@Component({
  selector: 'app-seller-show-suppliers',
  imports: [],
  templateUrl: './seller-show-suppliers.html',
  styleUrl: './seller-show-suppliers.css',
})
export class SellerShowSuppliers {
  private helperService = inject(HelperService);
  suppliers = signal(<SupplierResponseDTO[]>[]);
  private supplierService = inject(SupplierService);


 suppliersMock: SupplierResponseDTO[] = [
  {
    name: 'Distribuidora Tech Nordeste',
    cnpj: '12.345.678/0001-90',
    contactSupplierType: ContactSupplierType.EMAIL,
    contactValue: 'contato@technordeste.com.br',
    supplierItems: [
      {
        id: '550e8400-e29b-41d4-a716-446655440001',
        supplierName: 'Distribuidora Tech Nordeste',
        cprod: 'MOU-RGB-001',
        lastPrice: 89.90,
        item: {
          id: '550e8400-e29b-41d4-a716-446655440010',
          categoryName: 'Periféricos',
          name: 'Mouse Gamer RGB',
          about: 'Mouse sem fio com iluminação RGB.',
          price: 129.90,
          stock: 15,
          deleted: false,
          imagePath1: '/images/mouse1.jpg',
          imagePath2: null,
          imagePath3: null
        }
      },
      {
        id: '550e8400-e29b-41d4-a716-446655440002',
        supplierName: 'Distribuidora Tech Nordeste',
        cprod: 'TEC-MEC-002',
        lastPrice: 159.90,
        item: {
          id: '550e8400-e29b-41d4-a716-446655440011',
          categoryName: 'Periféricos',
          name: 'Teclado Mecânico',
          about: 'Teclado mecânico com switch azul.',
          price: 249.90,
          stock: 8,
          deleted: false,
          imagePath1: '/images/keyboard1.jpg',
          imagePath2: null,
          imagePath3: null
        }
      }
    ]
  },
  {
    name: 'Importadora Alpha',
    cnpj: '98.765.432/0001-10',
    contactSupplierType: ContactSupplierType.PHONE,
    contactValue: '(11) 99999-9999',
    supplierItems: [
      {
        id: '550e8400-e29b-41d4-a716-446655440003',
        supplierName: 'Importadora Alpha',
        cprod: 'MON-24-001',
        lastPrice: 599.90,
        item: {
          id: '550e8400-e29b-41d4-a716-446655440012',
          categoryName: 'Monitores',
          name: 'Monitor Full HD 24"',
          about: 'Monitor IPS Full HD de 24 polegadas.',
          price: 799.90,
          stock: 12,
          deleted: false,
          imagePath1: '/images/monitor1.jpg',
          imagePath2: null,
          imagePath3: null
        }
      }
    ]
  },
  {
    name: 'Mega Componentes',
    cnpj: '11.222.333/0001-44',
    contactSupplierType: ContactSupplierType.EMAIL,
    contactValue: 'vendas@megacomponentes.com.br',
    supplierItems: [
      {
        id: '550e8400-e29b-41d4-a716-446655440004',
        supplierName: 'Mega Componentes',
        cprod: 'SSD-1TB-001',
        lastPrice: 279.90,
        item: {
          id: '550e8400-e29b-41d4-a716-446655440013',
          categoryName: 'Armazenamento',
          name: 'SSD NVMe 1TB',
          about: 'SSD NVMe PCIe 4.0 de alta velocidade.',
          price: 399.90,
          stock: 20,
          deleted: false,
          imagePath1: '/images/ssd1.jpg',
          imagePath2: null,
          imagePath3: null
        }
      },
      {
        id: '550e8400-e29b-41d4-a716-446655440005',
        supplierName: 'Mega Componentes',
        cprod: 'RAM-16GB-001',
        lastPrice: 189.90,
        item: {
          id: '550e8400-e29b-41d4-a716-446655440014',
          categoryName: 'Memória',
          name: 'Memória DDR4 16GB',
          about: 'Módulo DDR4 3200MHz.',
          price: 249.90,
          stock: 30,
          deleted: false,
          imagePath1: '/images/ram1.jpg',
          imagePath2: null,
          imagePath3: null
        }
      }
    ]
  }
];

  // Total de fornecedores parceiros cadastrados
  totalSuppliersCount = computed(() => this.suppliers().length);

  // Total de produtos do catálogo que possuem algum vínculo de fornecimento
  totalLinkedItems = computed(() => 
    this.suppliers().reduce((acc, supplier) => acc + supplier.supplierItems.length, 0)
  );

  ngOnInit(){
    this.helperService.setAtualPage(4);
    this.supplierService.getSuppliersOfCatalog().subscribe({
      next: (data) =>{
        this.suppliers.set(data);
      }
    })
  }
}
