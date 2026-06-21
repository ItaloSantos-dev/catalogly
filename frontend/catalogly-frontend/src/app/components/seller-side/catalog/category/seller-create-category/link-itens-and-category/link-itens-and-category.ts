import { Component, EventEmitter, Input, Output, signal } from '@angular/core';
import { ItemResponseDTO } from '../../../../../../../types/item/item-response';

@Component({
  selector: 'app-link-itens-and-category',
  imports: [],
  templateUrl: './link-itens-and-category.html',
  styleUrl: './link-itens-and-category.css',
})
export class LinkItensAndCategory {
  @Input() items! : ItemResponseDTO[];
  @Input() itensHasSelected! : string[];

  itemsSelected = signal(<string[]>[])
  @Output() closeModal = new EventEmitter<void>();

  closingModal(){
    this.itemsSelected.set([])
    this.closeModal.emit();
  }

  @Output() sendItens = new EventEmitter<string[]>();
  sendItensSelected(){
    this.sendItens.emit(this.itemsSelected());
    this.closeModal.emit();
  }

  selectItem(id:string){
    if(this.itemsSelected().includes(id)){
      this.itemsSelected.update(items =>
        items.filter(itemId => itemId !== id)
      );
    }
    else{
      this.itemsSelected.update(items => [...items, id])
    }
    
  }

  ngOnInit(){
    this.itemsSelected.set(this.itensHasSelected);
  }


}
