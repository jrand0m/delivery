/**
 *
 */
package jobs;

import models.Comment;
import models.Restaurant;
import play.Logger;
import services.RestaurantService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Mike
 */
//todo: via akka @On("0 0 4 * * ?")
public class UpdateRatings {
    private static long MILLISECONDS_IN_DAY = 1000/* ms */ * 60/* s */ * 60/* m */ * 24/* h */;
    @Inject
    private static RestaurantService restaurantService;

    //@Override
    public void doJob() {
        //String rrtg = PropertyVault.getSystemValueFor("ratingsRefreshTimeGap");

        Long gapInDays = null;
        /*if (rrtg != null) {
              gapInDays = Long.parseLong(rrtg);
          *///} else {
        Logger.warn("UpdateRatings Job: couldn't load 'ratingsRefreshTimeGap' system value defaulting to 30 days");
        gapInDays = 30L;
        //}

        List<Comment> comments = restaurantService.findAllCommentsFromLastMonth();
        if (comments != null && !comments.isEmpty()) {
            Logger.info(String.format(
                    "UpdateRatings Job: starting to collect ratings for last %s days",
                    gapInDays));
            Long startTime = System.currentTimeMillis();
            LinkedHashMap<Restaurant, ArrayList<Integer>> map = new LinkedHashMap<Restaurant, ArrayList<Integer>>();
            for (Comment comment : comments) {
                Restaurant rest = comment.restaurant;
                if (!map.containsKey(rest)) {
                    map.put(rest, new ArrayList<Integer>());
                }
                ArrayList<Integer> list = map.get(rest);
                list.add(comment.commonRating);
            }
            Logger.info(String.format(
                    "UpdateRatings Job: Collecting finished in %s s. Starting refresh...",
                    (System.currentTimeMillis() - startTime) / 1000F));
            Iterator<Restaurant> iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                Restaurant r = iterator.next();
                ArrayList<Integer> ratings = map.get(r);
                Integer totals = 0;
                for (Integer rating : ratings) {
                    totals += rating;
                }
                Integer average = totals / ratings.size();
                if (average > 5) {
                    average = 5;
                } else if (average < 1) {
                    average = 1;
                }

                restaurantService.updateRating(r.getId(), average);
            }
            Logger.info(String.format(
                    "UpdateRatings Job: Finished refreshing (total time: %s s)",
                    (System.currentTimeMillis() - startTime) / 1000F));
        } else {
            Logger.warn("UpdateRatings Job: No data to process!");
        }

    }
}
