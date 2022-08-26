import java.util.*;

public class MovieRentingSystem
{
    private Map<Integer, SortedSet<RentalInfo>> avlLog;  // Maps movie to Sorted Set RentalInfo
    private Map<Integer, Map<Integer, RentalInfo>> movieToShopToInfo;  // Map from shop to RentalInfo
    private SortedSet<RentalInfo> rented;  // Maps price to info about rented movie
    private Comparator<RentalInfo> comparator;

    public MovieRentingSystem(int n, int[][] entries)
    {
        this.comparator = new RentalInfoComparator();

        this.rented = new TreeSet<>(this.comparator);
        this.avlLog = new HashMap<>();
        this.movieToShopToInfo = new HashMap<>();

        for (int i = 0; i < entries.length; i++)
        {
            RentalInfo rentalInfo = new RentalInfo(entries[i][0], entries[i][1], entries[i][2]);

            if (!this.avlLog.containsKey(rentalInfo.getMovie()))
            {
                this.avlLog.put(rentalInfo.getMovie(), new TreeSet<>(this.comparator));
            }
            if (!this.movieToShopToInfo.containsKey(rentalInfo.getMovie()))
            {
                this.movieToShopToInfo.put(rentalInfo.getMovie(), new HashMap<>());
            }

            this.avlLog.get(rentalInfo.getMovie()).add(rentalInfo);
            this.movieToShopToInfo.get(rentalInfo.getMovie()).put(rentalInfo.getShop(), rentalInfo);
        }
    }

    public List<Integer> search(int movie)
    {
        List<Integer> shops = new ArrayList<>();
        if (this.avlLog.containsKey(movie))
        {
            Iterator<RentalInfo> iterator = this.avlLog.get(movie).iterator();
            while (iterator.hasNext() && shops.size() < 5)
            {
                shops.add(iterator.next().getShop());
            }
        }
        return shops;
    }

    public void rent(int shop, int movie)
    {
        RentalInfo rentalInfo = this.movieToShopToInfo.get(movie).get(shop);
        this.avlLog.get(movie).remove(rentalInfo);
        this.rented.add(rentalInfo);
    }

    public void drop(int shop, int movie)
    {
        RentalInfo rentalInfo = this.movieToShopToInfo.get(movie).get(shop);
        this.rented.remove(rentalInfo);
        this.avlLog.get(movie).add(rentalInfo);
    }

    public List<List<Integer>> report()
    {
        List<List<Integer>> cheapestRentedMovies = new ArrayList<>();
        Iterator<RentalInfo> sortedRentalInfo = this.rented.iterator();
        while (sortedRentalInfo.hasNext() && cheapestRentedMovies.size() < 5)
        {
            RentalInfo rentalInfo = sortedRentalInfo.next();
            cheapestRentedMovies.add(new ArrayList<>(Arrays.asList(rentalInfo.getShop(), rentalInfo.getMovie())));
        }
        return cheapestRentedMovies;
    }

    private class RentalInfo
    {
        private int shop;
        private int movie;
        private int price;
        public RentalInfo(int shop, int movie, int price)
        {
            this.shop = shop;
            this.movie = movie;
            this.price = price;
        }

        public int getShop() {
            return shop;
        }

        public int getMovie() {
            return movie;
        }

        public int getPrice() {
            return price;
        }
    }

    private static class RentalInfoComparator implements Comparator<RentalInfo>
    {
        public int compare(RentalInfo r1, RentalInfo r2)
        {
            if (r1.getPrice() < r2.getPrice())
            {
                return -1;
            } else if (r1.getPrice() > r2.getPrice()) {
                return 1;
            } else {
                if (r1.getShop() < r2.getShop())
                {
                    return -1;
                } else if (r1.getShop() > r2.getShop())
                {
                    return 1;
                } else {
                    if (r1.getMovie() < r2.getMovie())
                    {
                        return -1;
                    } else if (r1.getMovie() > r2.getMovie())
                    {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            }
        }
    }
}