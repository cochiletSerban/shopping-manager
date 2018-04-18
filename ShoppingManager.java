import java.util.*;
import java.text.*;
import java.io.*;
interface Observer
{
  public void update(Notification notification);
}

interface Subject
{
  public void addObserver(Customer Customer);
  public void removeObserver(Customer Customer);
  public void notifyAllObservers(Notification notification);
}

interface Visitor
{
   public void  visit(BookDepartment bookDepartment);
   public void  visit(MusicDepartment musicDepartment);
   public void  visit(SoftwareDepartment softwareDepartment);
   public void  visit(VideoDepartment videoDepartment);
}

interface Visitable
{
   public void accept(Visitor visitor);
}

enum NotificationType
{
   ADD,REMOVE,MODIFY
}

class Notification 
{
   DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
   Date date;
   int IDdep,IDprodus;
   NotificationType type;
   double pret;
   public Notification(NotificationType type,int IDdep,int IDprodus)
   {
      date = new Date();
      this.IDdep=IDdep;
      this.IDprodus=IDprodus;
      this.type=type;
   } 
   public Notification(NotificationType type,int IDdep,int IDprodus,double pret)
   {
      date = new Date();
      this.IDdep=IDdep;
      this.IDprodus=IDprodus;
      this.type=type;
      this.pret=pret;
   } 
   public String toString()
   {
      return type+";"+IDprodus+";"+IDdep;
   }
}

class Item
{
   private int ID;
   private double pret;
   private String nume;
   public void setNume(String nume)
   {
      this.nume=nume;
   }
   public String getNume()
   {
      return this.nume;
   }
   public void setPret(double pret)
   {
      this.pret=pret;
   }
   public double getPret()
   {
      return this.pret;
   }
   public void setID(int ID)
   {
      this.ID=ID;
   }
   public int getID()
   {
      return this.ID;
   }
   public Item(String nume, double pret,int ID)
   {
      this.nume=nume;
      this.pret=pret;
      this.ID=ID;
   }
   public String toString()
   {
      return this.nume+";"+this.ID+";"+String.format("%.2f",pret);
   }
}

abstract class ItemList  implements Iterable 
{
   ArrayList <Item>lista = new ArrayList<Item>();
   int length=0;
   boolean sorted=true;
   Comparator c ;
   static class Node<Item>
   {
      Item item;
      Node<Item> next;
      Node<Item> prev;
      public Node(Item item, Node<Item> next,Node<Item> prev)
      {
         this.item = item;
         this.next = next;
         this.prev = prev;
      }
   }
   Node<Item> head;
   public void sortList()
   {
      if(!sorted)
      {
         Node<Item> aux = head; 
         if (head==null) return;
         while(aux!=null)
         {
            lista.add(aux.item);
            aux=aux.next;
         }
         Collections.sort(lista,c);
         head = null;
         for(Item i:lista)
            this.sadd(i);
         length=lista.size();
         lista.clear();
         sorted=true;
      } 
   }
   public boolean isEmpty()
   {
      return head == null;
   }
   public void set(int index,Item item)
   {
      this.remove(item);
      this.add(item);
   }
   public boolean add(Item item)
   {
      try
      {
         if( head == null)
         {
            length=1;
            sorted=true;
            head = new Node<Item>(item, null,null);
         }
         else
         {
            length++;
            sorted = false;
            Node<Item> tmp = head;
            while(tmp.next != null) tmp = tmp.next;
            tmp.next = new Node<Item>(item, null,tmp);
         }
         return true;
      }
      catch (Exception e)
      {
         return false;
      }
   }
   public boolean sadd(Item item)
   {
      sorted=true;
      try
      {
         if( head == null)
         {
            head = new Node<Item>(item, null,null);
         }
         else
         {
            Node<Item> tmp = head;
            while(tmp.next != null) tmp = tmp.next;
            tmp.next = new Node<Item>(item, null,tmp);
         }
         return true;
      }
      catch (Exception e)
      {
         return false;
      }
   }
   public boolean addAll(Collection c)
   {
      try
      {
         for (Object i:c)
            this.add((Item)i);
         return true;
      }
      catch (Exception e)
      {
         return false;
      } 
   }
   public Item getItem(int index)
   {
      Node<Item> aux = head; 
      if(aux!=null)
      {
         for(int i=0;i<index;i++)
            if(aux.next!=null)
               aux=aux.next;
            else throw new RuntimeException("No Item");
         return aux.item;
      }
      else throw new RuntimeException("No Item");
   }
   public Node<Item> getNode(int index)
   {
      Node<Item> aux = head;
      if(aux!=null)
      {
         for(int i=0;i<index;i++)
            aux=aux.next;
         return aux;
      }
      else return null;
   }
   public int indexOf(Item item)
   {
      Node<Item> aux = head; 
      int index=0;
      while(aux!=null)
      {
         if(aux.item.getID()==item.getID()) return index;
         aux=aux.next;
         index++;
      }
      return -1;
   }
   public int indexOf(Node <Item> nod)
   {
      Node<Item> aux = head;
      int index=0;
      while(aux!=null)
      {
         if(c.compare(aux.item,nod.item)==0) return index;
         aux=aux.next;
         index++;
      }
      return -1;
   }
   public boolean contains(Node <Item> nod)
   {
      Node<Item> aux = head; 
      while(aux!=null)
      {
         if(c.compare(aux.item,nod.item)==0) return true;
         aux=aux.next;
      }
      return false; 
   }
   public boolean contains(Item item)
   {
      Node<Item> aux = head; 
      while(aux!=null)
      {
         if(aux.item.getID()==item.getID()) return true;
         aux=aux.next;
      }
      return false; 
   }

   public boolean remove(Item item)
   {
      if(head == null)
         return false;

      if(head.item.equals(item))
      {
         length--;
         head = head.next;
         return true;
      }
      Node<Item> cur  = head;
      Node<Item> prev = null;
      Node<Item> next = null;
      while(cur != null && !cur.item.equals(item))
      {
         prev = cur;
         cur = cur.next;
      }
      if(cur == null)
         return false;
      next = cur.next;
      
      prev.next = next;
      if(next!=null)
         next.prev = prev;
      length--;
      return true;
   }
   public Item remove(int index)
   {
      if(head == null)
         throw new RuntimeException("cannot delete");
      if(index==0)
      {
         Item aux= head.item;
         head.prev=null;
         head = head.next;
         return aux;
      }
      Node<Item> cur  = head;
      Node<Item> prev = null;
      Node<Item> next = null;
      int i=0;
      while(cur != null && i!=index)
      {
         prev = cur;
         cur = cur.next;
         i++;
      }
      if(cur == null)
         throw new RuntimeException("cannot delete");
      Item aux = cur.item;
      next = cur.next;
      prev.next = next;
      if(next!=null)
         next.prev = prev;
      return aux;
   }
   public boolean removeAll(Collection c)
   {
      try
      {
         for (Object i:c)
         {
            if(this.indexOf((Item)i)>=0)
               this.remove((Item)i);
         }
         return true;
      }
      catch(Exception e)
      {
         return false;
      } 
   }
   abstract public double getTotalPrice();
   public String toString()
   {
      String output;
      if(!sorted)
         this.sortList();
      if(head!=null)
      {
         StringBuffer out = new StringBuffer();
         for(Object i:this)
         {
            out.append(i.toString()+", ");
         }
         output = "["+out.toString()+"]";
         out.setLength(0);
         String[] tmp = output.split(", ");
         int j=0;
         for(String i: tmp)
         {
            if(j+2==tmp.length) out.append(i);
            else
            {
               out.append(i+", ");
               j++;
            } 
         }
         return out.toString();
      }
      else return "[]";
   }
   public Iterator<Item> iterator()
   {
      return new DllIterator();
   }
   public Iterator<Item> iterator(int index)
   {
      return new DllIterator(index);
   }
   class DllIterator  implements Iterator<Item>
   {
      Node<Item> nextNode;
      Node<Item> prevNode;
      public DllIterator()
      {
         if(!sorted)
            ItemList.this.sortList();
         nextNode = head;
         prevNode = head;
         if(head!=null)
            while(prevNode.next!=null) prevNode=prevNode.next;
      }
      public DllIterator(int index)
      {
         if(!sorted)
            ItemList.this.sortList();
         nextNode = head;
         prevNode = head;
         if(head!=null)
            while(prevNode.next!=null) prevNode=prevNode.next;
         int i=0;
         while(i!=index)
         {
            this.next();
            i++;
         }
      }
      public boolean hasNext()
      {
         if(!sorted)
            ItemList.this.sortList();
         if(nextNode!=null)
            return true;
         else return false;
      }
      public boolean hasPrev()
      {
         if(!sorted)
            ItemList.this.sortList();
         if(prevNode!=null)
            return true;
         else return false;
      }
      public Item next()
      {
        if(!sorted)
            ItemList.this.sortList();
         if (!hasNext()) throw new RuntimeException("no element");
         Item aux = nextNode.item;
         nextNode = nextNode.next;
         return aux;
      }
      public Item prev()
      {
         if(!sorted)
            ItemList.this.sortList();
         if (!hasPrev()) throw new RuntimeException("no element");
         Item aux = prevNode.item;
         prevNode = prevNode.prev;
         return aux;
      }
      public int nextIndex()
      {
         if(nextNode!=null)
            return ItemList.this.indexOf(nextNode);
         else return -1;
      }
       public int prevIndex()
      {
         if(prevNode!=null)
            return ItemList.this.indexOf(prevNode);
         else return -1;
      }
      public void remove()
      {
         ItemList.this.remove(nextNode.item);
      }
      public void add(Item item)
      {
         ItemList.this.add(item);
      }
      public void set(Item item)
      {
         ItemList.this.remove(item);
         ItemList.this.add(item);
      }
   }
}

interface Strategy 
{
   public Item excute(WishList wishList);
}

class StrategyA implements Strategy
{
   public Item excute(WishList wishList)
   {
      Item min=wishList.head.item;
      for(Object i:wishList)
         if(min.getPret()>((Item)i).getPret())
             min=(Item)i;
      return min;
   }
}

class StrategyB implements Strategy
{
   public Item excute(WishList wishList)
   {
      wishList.sortList();
      return wishList.head.item;
   }
}

class StrategyC implements Strategy
{
   public Item excute(WishList wishList)
   {
      return wishList.lastItem;
   }
}

class WishList extends ItemList 
{
  Strategy strategyType = null;
  Item lastItem;
  public Item excuteStrategy()
  {
     return strategyType.excute(this);
  }
  public Strategy setStrategy(char st)
  {
      switch(st)
      {
         case 'A': 
            strategyType = new StrategyA();
            break;
         case 'B': 
            strategyType = new StrategyB();
            break;
         case 'C': 
            strategyType = new StrategyC();
            break;
      }
      return null;
  }

   class comparator implements Comparator
   {
      public int compare(Object o1,Object o2)
      {
          return ((Item)o1).getNume().compareTo(((Item)o2).getNume());
      }
   }
   public WishList()
   {
      this.head = null;
      this.c = new comparator();
   }
   public double getTotalPrice()
   {
      double total=0;
      for(Object i:this)
      {
         total+=((Item)i).getPret();
      }
      return total;
   }
}

class ShoppingCart extends ItemList implements Visitor
{
   double buget;
   class comparator implements Comparator
   {
      public int compare(Object o1,Object o2)
      {
          if(((Item)o1).getPret()==(((Item)o2).getPret())) 
            return ((Item)o1).getNume().compareTo(((Item)o2).getNume());
         return (int)(((Item)o1).getPret()-(((Item)o2).getPret()));
      }
   }
   public ShoppingCart(double buget)
   {
      this.buget=buget;
      this.head = null;
      this.c = new comparator();
   }
   public boolean add(Item item)
   {
      if(buget-item.getPret()>=0)
      {
         buget-=item.getPret();
         return super.add(item);
      }
      else return false;
   }
   public boolean remove(Item item)
   {
      if(super.remove(item)) 
      {
         buget+=item.getPret();
         return true;
      }
      else return false;
   }
   public Item remove(int index)
   {
      Item aux=super.remove(index);     
      buget+=aux.getPret();
      return aux;
   }
   public double getTotalPrice()
   {
      double total=0;
      for(Object i:this)
      {
         total+=((Item)i).getPret();
      }
      return total;
   }
   public void  visit(BookDepartment bookDepartment)
   {
      for(Item i:bookDepartment.getItems())
      {
         if(this.contains(i))
         {
            Item aux = this.remove(this.indexOf(i));
            aux.setPret(aux.getPret()-(0.1000*aux.getPret()));
            this.add(aux);
         }
      }  
   }
   public void  visit(MusicDepartment musicDepartment)
   {
      double total=0;
      for(Item i:musicDepartment.getItems())
         if(this.contains(i))
           total+=i.getPret()*0.1000;
      buget+=total;
   }
   public void visit(SoftwareDepartment softwareDepartment)
   {
      double min = Double.MAX_VALUE;
      for(Item i:softwareDepartment.getItems())
         if(i.getPret()<min) min = i.getPret();
      for(Item i:softwareDepartment.getItems())
      {
         if(this.contains(i)&&this.buget<min)
         {
            Item aux = this.remove(this.indexOf(i));
            aux.setPret(aux.getPret()-(0.2000*aux.getPret()));
            this.add(aux);
         }
      }
   }
   public void  visit(VideoDepartment videoDepartment)
   {
      double max = Double.MIN_VALUE;
      double total=0;
      double reducere=0;
      for(Item i:videoDepartment.getItems())
      {
         if(i.getPret()>max) max = i.getPret();
         if(this.contains(i))
         {
            reducere+=i.getPret()*0.0500;
            total+=i.getPret();
         }
      }
      buget+=reducere;
      for(Item i:videoDepartment.getItems())
      {  
         if(this.contains(i)&&total>max)
         {
            Item aux = this.remove(this.indexOf(i));
            aux.setPret(aux.getPret()-(0.1500*aux.getPret()));
            this.add(aux);
         }
      }
   }
}

class Store
{
   ArrayList <Customer> Customers; 
   ArrayList <Department> Departments;
   private static Store instance = null;
   String name;
   Department lastDep = null;
   public void enter(Customer Customer)
   {
      if(!Customers.contains(Customer))
         Customers.add(Customer);
   }
   public void exit(Customer Customer)
   {
      Customers.remove(Customer);
   }
   public ShoppingCart getShoppingCart(double budget)
   {
      return new ShoppingCart(budget);
   }
   public ArrayList <Customer> getCustomers()
   {
      return Customers;
   }
   public ArrayList <Department> getDepartments()
   {
      return Departments;
   }
   public void addDepartment(Department... departments)
   {
      for(Department department:departments)
         Departments.add(department);
   }
   public Department getDepartment(int nr)
   {
       for(Department d:Departments)
         if (d.ID==nr)
            return d;
      return null;
   }
   public Department getDepByItemID(int ID)
   {
      for(Department d: Departments)
      {
         for(Item i:d.getItems())
         {
               if(i.getID()==ID) return d; 
         }
      }
      return new MusicDepartment("NotAnItem",-1);
   }
   public Item getItemByID(int ID)
   {
      for(Department d:Departments)
         for(Item i:d.getItems())
         {
            if(i.getID()==ID)
            {
               lastDep=d;
               return i;
            }    
         }  
      return new Item("err la GetIT",-1,-1);
   }
   public void addProduct(int ID,int prodId,double prodPret,String prodNume)
   {
      Item newProd = new Item(prodNume,prodPret,prodId);
      for(Department d:Departments)
         if(d.ID==ID) d.addItem(ID,newProd);
   }
   public void delProduct(int ID)
   {
     if(getDepByItemID(ID).nume.equals("NotAnItem")) return;
      for(Department d:Departments)
         d.delItem(ID);
   }
   public void addItemTo(String who,String to,int ID)
   {
      for (Customer c:Customers)
         c.addItemTo(who,to,this.getItemByID(ID),lastDep);
   }
   public ItemList getItems(String from,String who)
   {
      for (Customer c:Customers)
      {
         if(c.getItems(from,who)!=null) 
            return c.getItems(from,who);
      }
      return null;
   }
   public String getTotal(String from,String who)
   {
      for (Customer c:Customers)
         if(c.getTotal(who,from)!=-1) return String.format("%.2f",c.getTotal(who,from));
      return "-1";
   }
   public Department getDepById(int ID)
   {
      for (Department d:Departments)
         if(d.ID==ID) return d;
      return null;
   }
   public void accept(String who,int depID)
   {
      Department d=getDepById(depID);
      if(d!=null)
         for(Customer c:Customers)
            if(c.nume.equals(who)) 
               d.accept(c.getShoppingCart(who));
   }
   public ArrayList <Customer> getObservers(int depID)
   {
      return getDepById(depID).observers;
   }
   public ArrayList <Notification> getNotifications(String who)
   {
      for (Customer c:Customers)
         if (c.nume.equals(who)) return c.notifications;
      return new ArrayList();
   }
   public void modifyProduct(int itemId,int depId,double pret)
   {
      for(Department d:Departments)
         d.modifyItem(itemId,depId,pret);
   }
   public void delItemFrom(String who,String from,int itemId)
   {
      for (Customer c:Customers)
      {
         c.delItemFrom(who,from,this.getItemByID(itemId),lastDep);
      }
   }
   public Item getItem(String name)
   {
      Item aux=null;
      boolean last=false;
      Department dep=null;
      Customer cost=null;
      for (Customer c:Customers)
      {
         if(c.nume.equals(name))
         {
            aux = c.wishList.excuteStrategy();
            cost=c;
         }
      }
      for(Department d:Departments)
         for(Item i:d.getItems())
            if(aux.getID()==i.getID())
            {
               dep=d;
               break;
            }
      if(cost.addItemTo(cost.nume,"ShoppingCart",aux,dep))
         if(dep!=null)
            cost.delItemFrom (cost.nume,"WishList",aux,dep);
      return aux;
   }
   private Store(String name)
   {
      Departments = new ArrayList();
      Customers = new ArrayList();
      this.name=name;
   }
   public static Store getStore(String name)
   {
      if(instance==null)
         instance=new Store(name);
      return instance;
   }
} 

abstract class Department implements Subject , Visitable
{
   String nume;
   int ID;
   ArrayList<Item>  Items;
   ArrayList <Customer> Customers; 
   public void enter(Customer Customer)
   {
      if(!Customers.contains(Customer))
         Customers.add(Customer);
      if(!observers.contains(Customer))
         addObserver(Customer);
   }
   public void exit(Customer Customer)
   {
      removeObserver(Customer);
   }
   public ArrayList <Customer> getCustomers()
   {
      return this.Customers;
   }
   public int getId()
   {
      return this.ID;
   }
   public ArrayList<Item> getItems()
   {
      return this.Items;
   }
   public void addItems(int departID,ArrayList <Item> items)
   {
      for (Item item:items)
         this.addItem(departID,item);
   }
   public boolean addItem(int depID ,Item item)
   {
      if(depID==ID)
         {
            Items.add(item);
            NotificationType type = NotificationType.ADD;
            notifyAllObservers(new Notification(type,this.ID,item.getID()));
            return true;
         }
      else return false;
   }
   public void modifyItem(int itemID,int departID,double pret)
   {
      int index=-1;
      if(this.ID==departID)
         for(Item item:Items)
            if(item.getID()==itemID)
               index=Items.indexOf(item);
      if(index>=0) 
      {
         Item tmp=new Item(Items.get(index).getNume(),Items.get(index).getPret(),Items.get(index).getID());
         tmp.setPret(pret);
         Items.set(index,tmp);
         NotificationType type = NotificationType.MODIFY;
         notifyAllObservers(new Notification(type,this.ID,itemID,pret));
      }
   }
   public void delItem(int itemID)
   {
      int index=-1;
      for (Item item:Items)
         if (item.getID()==itemID)
            index = Items.indexOf(item);
      if(index>=0)
      {  
         Items.remove(index);
         NotificationType type = NotificationType.REMOVE;
         notifyAllObservers(new Notification(type,this.ID,itemID));
      }  
   }
   ////////////////////observer pattern stuff/////////////////
   ArrayList <Customer> observers;
   public void addObserver(Customer Customer)
   {
      observers.add(Customer);
   }
   public void removeObserver(Customer Customer)
   {
      observers.remove(Customer);
   }
   public void notifyAllObservers(Notification notification)
   {
      for(Customer Customer:observers)
         Customer.update(notification);
   }
   public abstract void accept(Visitor visitor);
}

class BookDepartment extends Department
{
   public BookDepartment(String nume,int ID)
   {
      this.nume = nume;
      this.ID=ID;
      this.observers = new ArrayList();
      this.Items = new ArrayList();
      this.Customers=new ArrayList();
   }
   public void accept(Visitor visitor) 
   {
      visitor.visit(this);
   }
}

class MusicDepartment extends Department
{
   public MusicDepartment(String nume,int ID)
   {
      this.nume = nume;
      this.ID=ID;
      this.observers = new ArrayList();
      this.Items= new ArrayList();
      this.Customers=new ArrayList();
   }
   public void accept(Visitor visitor) 
   {
      visitor.visit(this);
   }
}

class SoftwareDepartment extends Department
{
   public SoftwareDepartment(String nume,int ID)
   {
      this.nume = nume;
      this.ID=ID;
      this.observers = new ArrayList();
      this.Items= new ArrayList();
      this.Customers=new ArrayList();
   }
   public void accept(Visitor visitor) 
   {
      visitor.visit(this);
   }
}

class VideoDepartment extends Department
{
   public VideoDepartment(String nume,int ID)
   {
      this.nume = nume;
      this.ID=ID;
      this.observers = new ArrayList();
      this.Items= new ArrayList();
      this.Customers=new ArrayList();
   }
   public void accept(Visitor visitor) 
   {
      visitor.visit(this);
   }
}

class Customer implements Observer 
{
   String nume;
   ShoppingCart shoppingCart;
   WishList  wishList;
   Store store;
   public Customer(String nume,Store store,double buget,char strategyType)
   {
      this.nume = nume;
      notifications = new ArrayList();
      wishList = new WishList();
      this.store=store;
      wishList.setStrategy(strategyType);
      shoppingCart = store.getShoppingCart(buget);
   }
   public double getTotal(String who,String from)
   {
      if(nume.equals(who))
      {
         if(from.equals("ShoppingCart")) return shoppingCart.getTotalPrice();
         else return wishList.getTotalPrice();
      }
      else return -1;
   }
   public boolean addItemTo(String who,String to,Item item,Department departament)
   {
      if(who.equals(nume))
      {
         switch(to)
         {
            case "WishList":
               wishList.add(item);
               wishList.lastItem=item;
               departament.enter(this);
               break;
            case "WishListM":
               wishList.add(item);
               break;
            case "ShoppingCart":
               return(shoppingCart.add(item));
         }
      }
      return false;
   }
   public boolean delItemFrom (String who,String from,Item item,Department dep)
   {
      boolean exit=true;
      int index=-1;
      if(who.equals(this.nume)&&item!=null&&dep!=null)
      {
         if(from.equals("WishList")) 
         {
            wishList.remove(item);
         }
         else 
         {
            shoppingCart.remove(item);
         }
         
         for(Item i:dep.getItems())
         {
            for(Object w:this.wishList)
               if(((Item)w).getID()==i.getID())
                  exit=false;
         }
         if(exit) dep.removeObserver(this);
      }
      return exit;
   }
   public ItemList getItems(String from ,String nume)
   {  
      if(nume.equals(this.nume) && from.equals("WishList")) return this.wishList;
      else if(nume.equals(this.nume) && from.equals("ShoppingCart")) return (ItemList)(this.shoppingCart);
      return null;
   }
   public ShoppingCart getShoppingCart(String who)
   {
      if(who.equals(this.nume)) return this.shoppingCart;
      return null;
   }
   public String toString()
   {
      return this.nume;
   }
   ////////////////////observer pattern stuff/////////////////
   ArrayList <Notification> notifications;
   public void update(Notification notification)
   {
      notifications.add(notification);
      switch(notification.type)
      {
         case ADD:
            break;
         case MODIFY:
            int index=-1;
            for(Object item:wishList)
               if(((Item)item).getID()==notification.IDprodus)
                  index=wishList.indexOf((Item)item);
            if(index>=0) 
            { 
               Item tmp=wishList.remove(index);
               Item aux=new Item(tmp.getNume(),notification.pret,tmp.getID());
               for(Department d: store.getDepartments())
                  for(Item i:d.getItems())
                     if(i.getID()==aux.getID())
                        this.addItemTo(this.nume,"WishListM",aux,d);
            }
            index=-1;
            for(Object item:shoppingCart)
               if(((Item)item).getID()==notification.IDprodus)
                  index=shoppingCart.indexOf((Item)item);
            boolean added=false;
            if(index>=0) 
            {
               Item tmp=shoppingCart.remove(index);
               tmp.setPret(notification.pret);
               added=shoppingCart.add(tmp);
            }
            break;
         case REMOVE:
            for (Object item:wishList)
               if (((Item)item).getID()==notification.IDprodus)
                  this.delItemFrom(nume,"WishList",(Item)item,store.getDepByItemID(notification.IDprodus));
            index=-1;
            for(Object item:shoppingCart)
               if(((Item)item).getID()==notification.IDprodus)
                  index=shoppingCart.indexOf((Item)item);
            if(index>=0) 
               shoppingCart.remove(index);
            break;
      }
   }
}

class fileParser
{
   BufferedReader bufferR;
   ArrayList <String> storeContent;
   public ArrayList <String> readFile(String fileName)
   {
      ArrayList <String> fileContent=null;
      try 
      {
         bufferR = new BufferedReader(new FileReader(fileName));
         String line = bufferR.readLine();
         fileContent=new ArrayList();
         if(line.length()<3) line="";
         while (line != null) 
         {  
            if(line!="") fileContent.add(line);
            line = bufferR.readLine();
         }
      } 
      catch (Exception e){}
      finally 
      {
         try{bufferR.close();}
         catch(Exception e){};
         return fileContent;
      }
   }
   public ArrayList <Customer> parseCustomers(String fileName,Store store)
   {
      ArrayList <Customer> Customers=new ArrayList();
      for(String i:readFile(fileName))
      {
         String [] perLine = i.split(";");
         Customers.add(new Customer(perLine[0],store,Double.parseDouble(perLine[1]),perLine[2].charAt(0)));
      }
      return Customers;
   }
   public String parseStore(String fileName)
   {
      storeContent = readFile(fileName);
      return storeContent.get(0);
   }
   public MusicDepartment parseMusic()
   {
      MusicDepartment music=null;  
      for(String i:storeContent)
      {
         String [] perLine = i.split(";");
         if(perLine[0].equals("MusicDepartment"))
            music = new MusicDepartment(perLine[0],Integer.parseInt(perLine[1]));
      }
      return music;
   }
   public SoftwareDepartment parseSoft()
   {
      SoftwareDepartment soft=null;  
      for(String i:storeContent)
      {
         String [] perLine = i.split(";");
         if(perLine[0].equals("SoftwareDepartment"))
            soft = new SoftwareDepartment(perLine[0],Integer.parseInt(perLine[1]));
      }
      return soft;
   }
   public VideoDepartment parseVideo()
   {
      VideoDepartment video=null;  
      for(String i:storeContent)
      {
         String [] perLine = i.split(";");
         if(perLine[0].equals("VideoDepartment"))
            video = new VideoDepartment(perLine[0],Integer.parseInt(perLine[1]));
      }
      return video;
   }
   public BookDepartment parseBook()
   {
      BookDepartment book=null;  
      for(String i:storeContent)
      {
         String [] perLine = i.split(";");
         if(perLine[0].equals("BookDepartment"))
            book = new BookDepartment(perLine[0],Integer.parseInt(perLine[1]));
      }
      return book;
   }
   public ArrayList<Item> parseItems(String dep)
   {
      ArrayList <Item> items=new ArrayList();  
      int j=Integer.MAX_VALUE;
      int readTo=0;
      boolean found = false;
      for(String i:storeContent)
      {
         String [] perLine = i.split(";");
         if(perLine[0].equals(dep))
         {
            found = true;
            continue;
         }
         if(found) 
         {
            readTo=Integer.parseInt(perLine[0]);
            j=0;
            found=false;
         }
         if(j<readTo&&perLine.length==3) 
         {
            items.add(new Item(perLine[0],Double.parseDouble(perLine[2]),Integer.parseInt(perLine[1])));
            j++;
         }
      }
      return items;
   }
   public void execute(Store store,String fileName)
   {
      for(String i:readFile(fileName))
      {
         String [] perLine = i.split(";");
         switch (perLine[0])
         {
            case "addItem":
               store.addItemTo(perLine[3],perLine[2],Integer.parseInt(perLine[1]));
               break;
            case "delItem":
               store.delItemFrom(perLine[3],perLine[2],Integer.parseInt(perLine[1]));
               break;
            case "addProduct":
               store.addProduct(Integer.parseInt(perLine[1]),Integer.parseInt(perLine[2]),Double.parseDouble(perLine[3]),perLine[4]);
               break;
            case "modifyProduct":
               store.modifyProduct(Integer.parseInt(perLine[2]),Integer.parseInt(perLine[1]),Double.parseDouble(perLine[3]));
               break;
            case "delProduct":
               store.delProduct(Integer.parseInt(perLine[1]));
               break;
            case "getItem":
               Item it=store.getItem(perLine[1]);
               System.out.println(it);
               break;
            case "getTotal":
               System.out.println(store.getTotal(perLine[1],perLine[2]));
               break;
            case "getItems":
               System.out.println(store.getItems(perLine[1],perLine[2]));
               break;
            case "accept":
               store.accept(perLine[2],Integer.parseInt(perLine[1]));
               break;
            case "getObservers":
               System.out.println(store.getObservers(Integer.parseInt(perLine[1])));
               break;
            case "getNotifications":
               System.out.println(store.getNotifications(perLine[1]));
               break;
         }
      }
   }
}

class Test
{
   public static void main(String[] args) 
   {
      fileParser getEm = new fileParser();
      Store store = Store.getStore(getEm.parseStore("store.txt"));
      MusicDepartment musicDepartment=getEm.parseMusic();
      SoftwareDepartment softDepartment=getEm.parseSoft();
      BookDepartment bookDepartment=getEm.parseBook();
      VideoDepartment videoDepartment=getEm.parseVideo();
      musicDepartment.addItems(musicDepartment.ID,getEm.parseItems("MusicDepartment"));
      videoDepartment.addItems(videoDepartment.ID,getEm.parseItems("VideoDepartment"));
      bookDepartment.addItems(bookDepartment.ID,getEm.parseItems("BookDepartment"));
      softDepartment.addItems(softDepartment.ID,getEm.parseItems("SoftwareDepartment"));
      store.addDepartment(musicDepartment,softDepartment,bookDepartment,videoDepartment);
      for(Customer i:getEm.parseCustomers("customers.txt",store))
         store.enter(i);
      getEm.execute(store,"events.txt");                                       
   }
}