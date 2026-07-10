import { Component, computed, inject, signal } from '@angular/core';
import { SupplierService } from '../../../../../service/supplier/supplier-service';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { SupplierItemResponseDTO } from '../../../../../../types/supplier-item/supplier-item-response';

@Component({
  selector: 'app-seller-show-supplier-items',
  imports: [RouterLink],
  templateUrl: './seller-show-supplier-items.html',
  styleUrl: './seller-show-supplier-items.css',
})
export class SellerShowSupplierItems {
  private supplierService = inject(SupplierService);
  supplierItems = signal(<SupplierItemResponseDTO[]>[]);

  constructor (private route: ActivatedRoute){};

  // Computed inteligente para extrair o nome do fornecedor dinamicamente do primeiro item
  supplierName = computed(() => {
    const items = this.supplierItems();
    return items.length > 0 ? items[0].supplierName : 'Fornecedor';
  });

  averageCost = computed(() => {
  const items = this.supplierItems();
  if (items.length === 0) return 0;

  const total = items.reduce((acc, current) => acc + (current.lastPrice ?? 0), 0);
  return total / items.length;
});


  suppliersItemsMock: SupplierItemResponseDTO[] = [
  {
    id: "1",
    supplierName: "Fornecedor Alpha",
    cProd: "ALP-001",
    lastPrice: 79.90,
    supplierId:"01",
    item: {
      id: "item-1",
      categoryId: "cat-1",
      categoryName: "Ferramentas",
      name: "Furadeira de Impacto",
      about: "Furadeira elétrica 750W com impacto.",
      price: 99.90,
      stock: 15,
      deleted: true,
      imagePath1: "/images/furadeira.jpg",
      imagePath2: null,
      imagePath3: null,
    },
  },
  {
    id: "2",
    supplierName: "Fornecedor Beta",
    cProd: "BET-145",
    lastPrice: 18.50,
    supplierId:"01",
    item: {
      id: "item-2",
      categoryId: "cat-2",
      categoryName: "Elétrica",
      name: "Disjuntor Bipolar 32A",
      about: "Disjuntor bipolar curva C.",
      price: 24.90,
      stock: 42,
      deleted: false,
      imagePath1: "/images/disjuntor.jpg",
      imagePath2: null,
      imagePath3: null,
    },
  },
  {
    id: "3",
    supplierName: "Fornecedor Gama",
    cProd: "GAM-889",
    lastPrice: 245.00,
    supplierId:"01",
    item: {
      id: "item-3",
      categoryId: "cat-3",
      categoryName: "Motores",
      name: "Motor Elétrico 2CV",
      about: "Motor trifásico 220/380V.",
      price: 299.90,
      stock: 8,
      deleted: false,
      imagePath1: "/images/motor.jpg",
      imagePath2: "/images/motor2.jpg",
      imagePath3: null,
    },
  },
  {
    id: "4",
    supplierName: "Fornecedor Delta",
    cProd: "DEL-502",
    lastPrice: 5.75,
    supplierId:"01",
    item: {
      id: "item-4",
      categoryId: "cat-4",
      categoryName: "Fixação",
      name: "Parafuso Sextavado M8",
      about: "Parafuso galvanizado M8 x 40mm.",
      price: 8.50,
      stock: 350,
      deleted: false,
      imagePath1: "/images/parafuso.jpg",
      imagePath2: null,
      imagePath3: null,
    },
  },
  {
    id: "5",
    supplierName: "Fornecedor Omega",
    cProd: "OMG-990",
    lastPrice: 135.00,
    supplierId:"01",
    item: {
      id: "item-5",
      categoryId: "cat-5",
      categoryName: "Automação",
      name: "Contator 25A",
      about: "Contator tripolar para acionamento de motores.",
      price: 159.90,
      stock: 21,
      deleted: false,
      imagePath1: "/images/contator.jpg",
      imagePath2: "/images/contator2.jpg",
      imagePath3: "/images/contator3.jpg",
    },
  },
  ];


  ngOnInit(){
    
    
    const id = this.route.snapshot.paramMap.get("id");
    if (id) {
      this.supplierService.getItemOfSupplierById(id).subscribe({
        next:(data) =>{
          this.supplierItems.set(data)
          console.log(this.supplierItems()[0]);
          
          console.log(this.supplierItems()[0].cProd);
        },
        error: (err) => {
          console.error(err);
        }
      })
    }

  }
}
